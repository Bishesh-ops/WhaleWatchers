package com.analytics.DataCruncher.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeMessage(
    String ticker,
    String contractType,
    BigDecimal strikePrice,
    LocalDate expirationDate,
    BigDecimal totalPremium,
    Integer volume,
    ZonedDateTime timeStamp
) {}
