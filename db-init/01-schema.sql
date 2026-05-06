CREATE TABLE WhaleAlerts(
    Id UUID PRIMARY KEY,
    DetectedAt TIMESTAMP NOT NULL,
    TickerSymbol VARCHAR(10) NOT NULL,
    ContractType VARCHAR(10) NOT NULL,
    StrikePrice DECIMAL(18, 2) NOT NULL,
    TotalPremium DECIMAL(18, 2) NOT NULL,
    Volume INT NOT NULL,
    AnomalyReason VARCHAR(255) NOT NULL,
    ProcessedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_whale_ticker ON WhaleAlerts(TickerSymbol);

CREATE INDEX idx_whale_premium ON WhaleAlerts(TotalPremium DESC);