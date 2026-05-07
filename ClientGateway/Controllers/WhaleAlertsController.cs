using ClientGateway.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ClientGateway.Controllers;

[ApiController]
[Route("api/whales")]
public class WhaleAlertController : ControllerBase
{
    private readonly AppDbContext _context;

    public WhaleAlertController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet("top")]
    public async Task<IActionResult> GetTopWhales()
    {
        var topWhales = await _context.WhaleAlerts
            .OrderByDescending(w => w.TotalPremium)
            .Take(10)
            .ToListAsync();

        return Ok(topWhales); // Automatically serializes the list to JSON
    }

}