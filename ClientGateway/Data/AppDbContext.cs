using ClientGateway.Models;
using Microsoft.EntityFrameworkCore;

namespace ClientGateway.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    public DbSet<WhaleAlert> WhaleAlerts { get; set; }
}