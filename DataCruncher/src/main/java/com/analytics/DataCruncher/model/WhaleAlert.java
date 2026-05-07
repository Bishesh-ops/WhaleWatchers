package com.analytics.DataCruncher.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="WhaleAlerts")

public class WhaleAlert {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "DetectedAt", nullable = false)
    private LocalDateTime detectedAt;

    @Column(name = "TickerSymbol", nullable = false, length = 10)
    private String tickerSymbol;

    @Column(name = "ContractType", nullable = false, length = 10)
    private String contractType;

    @Column(name = "StrikePrice", nullable = false)
    private BigDecimal strikePrice;

    @Column(name = "ExpirationDate", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "TotalPremium", nullable = false)
    private BigDecimal totalPremium;

    @Column(name = "Volume", nullable = false)
    private Integer volume;

    @Column(name = "AnomalyReason", nullable = false)
    private String anomalyReason;

    @Column(name = "ProcessedAt", insertable = false, updatable = false)
    private LocalDateTime processedAt;

    public WhaleAlert() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getAnomalyReason() {
        return anomalyReason;
    }

    public void setAnomalyReason(String anomalyReason) {
        this.anomalyReason = anomalyReason;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
    


}
