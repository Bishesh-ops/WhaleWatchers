using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ClientGateway.Models;

[Table("whale_alerts")]
public class WhaleAlert
{
    [Key]
    [Column("id")]
    public Guid Id { get; set; }

    [Column("detected_at")]
    public DateTime DetectedAt { get; set; }

    [Column("ticker_symbol")]
    public string TickerSymbol { get; set; } = string.Empty;

    [Column("contract_type")]
    public string ContractType { get; set; } = string.Empty;

    [Column("strike_price")]
    public decimal StrikePrice { get; set; }

    [Column("expiration_date")]
    public DateOnly ExpirationDate { get; set; }

    [Column("total_premium")]
    public decimal TotalPremium { get; set; }

    [Column("volume")]
    public int Volume { get; set; }

    [Column("anomaly_reason")]
    public string AnomalyReason { get; set; } = string.Empty;

    [Column("processed_at")]
    public DateTime? ProcessedAt { get; set; }
}