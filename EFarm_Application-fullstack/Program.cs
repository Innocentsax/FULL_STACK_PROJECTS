

using EFarm.Server;
using EFarm.Server.Data;
using EFarm.Server.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers();
builder.Services.AddRazorPages();
builder.Services.AddServerSideBlazor();
builder.Services.AddTransient<IAppSession, AppSession>();
builder.Services.AddScoped<IApiService, ApiService>();
var apiUri = builder.Configuration["ApiUrl"] ?? "http://localhost:5000";
builder.Services.AddScoped(sp => new HttpClient
{
    BaseAddress = new Uri($"{apiUri}/api/"),
});
builder.Services.AddDbContext<AppDbContext>(x => x.UseSqlite());
builder.Services.AddScoped<ToastService>();
builder.Services.AddSingleton<AppState>();
builder.Services.AddHttpContextAccessor();
//var jwtSecretKey = builder.Configuration["Jwt:Secret"];
//var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(jwtSecretKey));
//builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
//    .AddJwtBearer(options =>
//    {
//        options.TokenValidationParameters = new TokenValidationParameters
//        {
//            ValidateIssuer = true,
//            ValidateAudience = true,
//            ValidateIssuerSigningKey = true,
//            ValidIssuer = builder.Configuration["Jwt:Issuer"],
//            ValidAudience = builder.Configuration["Jwt:Audience"],
//            IssuerSigningKey = key
//        };
//    });
builder.WebHost.UseStaticWebAssets();
var app = builder.Build();


// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();

app.UseStaticFiles();
app.UseAuthentication();
app.UseRouting();
app.UseAuthorization();
app.MapControllers();
app.MapBlazorHub();
app.MapFallbackToPage("/_Host");
using (var scope = app.Services.CreateScope())
{
    var scopedProvider = scope.ServiceProvider;
    var dbContext = scopedProvider.GetRequiredService<AppDbContext>();
    await dbContext.Database.EnsureDeletedAsync();
    await dbContext.Database.MigrateAsync();
    await Seed.Initialise(dbContext);


}
await app.RunAsync();
