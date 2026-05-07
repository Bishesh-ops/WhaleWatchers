package com.analytics.DataCruncher.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.analytics.DataCruncher.dto.TradeMessage;
import com.analytics.DataCruncher.model.WhaleAlert;
import com.analytics.DataCruncher.repository.WhaleAlertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

@Service
public class MarketStreamListener extends TextWebSocketHandler {

    private final WhaleAlertRepository repository;
    private final ObjectMapper objectMapper;

    private static final BigDecimal WHALE_THRESHOLD = new BigDecimal("10000000.00");

    public MarketStreamListener(WhaleAlertRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); 
    }

    @PostConstruct
    public void connectToFirehose() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            System.out.println("Booting up Market Listener... connecting to ShareSansar Engine.");
            client.execute(this, "ws://localhost:8765").get();
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            System.err.println("Failed to connect to WebSocket: " + e.getMessage());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        Thread.startVirtualThread(() -> processTrade(message.getPayload()));
    }

    private void processTrade(String jsonPayload) {
        try {
            TradeMessage trade = objectMapper.readValue(jsonPayload, TradeMessage.class);

            if (trade.totalPremium().compareTo(WHALE_THRESHOLD) >= 0) {
                System.out.println("WHALE DETECTED: " + trade.ticker() + " | Turnover: Rs. " + trade.totalPremium());

                WhaleAlert alert = new WhaleAlert();
                alert.setTickerSymbol(trade.ticker());
                alert.setContractType(trade.contractType()); // Will be "EQUITY"
                alert.setStrikePrice(trade.strikePrice()); 
                alert.setExpirationDate(trade.expirationDate()); 
                alert.setTotalPremium(trade.totalPremium());
                alert.setVolume(trade.volume());
                alert.setDetectedAt(java.time.LocalDateTime.now());
                alert.setAnomalyReason("Turnover Exceeds Rs.1 Crore NPR");
                
                repository.save(alert);
            }

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Database or System Error: " + e.getMessage());
        }
    }
}