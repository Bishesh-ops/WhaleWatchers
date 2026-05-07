using ClientGateway.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("WhaleDb")));

builder.Services.AddControllers();
var app = builder.Build();

app.MapControllers();
app.Run("http://localhost:5000");