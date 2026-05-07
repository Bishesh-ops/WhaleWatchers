using ClientGateway.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("WhaleDb")));

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll",
        corsBuilder => corsBuilder.AllowAnyOrigin()
                                  .AllowAnyMethod()
                                  .AllowAnyHeader());
});


builder.Services.AddControllers();

var app = builder.Build();

app.UseCors("AllowAll");

// 5. Map the endpoints and run the server
app.MapControllers();
app.Run("http://localhost:5000");