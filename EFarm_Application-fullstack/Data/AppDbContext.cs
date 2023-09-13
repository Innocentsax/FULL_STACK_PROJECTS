using EFarm.Server.Model;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace EFarm.Server.Data;
// Define the 'AppDbContext' class, which inherits from 'DbContext' for database interaction
public class AppDbContext : DbContext
{
	protected readonly IConfiguration Configuration;
	// Constructor for 'AppDbContext' that receives an 'IConfiguration' instance
	public AppDbContext(IConfiguration configuration)
	{
		// Initialize the 'Configuration' field with the provided configuration instance
		Configuration = configuration;
	}
	// Override the 'OnConfiguring' method to configure the database connection
	protected override void OnConfiguring(DbContextOptionsBuilder options)
	{
		// Configure the database connection to use SQLite based on the 'Default' connection string from the configuration
		options.UseSqlite(Configuration.GetConnectionString("Default"));
	}
	//public AppDbContext(DbContextOptions options) : base(options) {
	//}

	// Define DbSet properties to represent database tables
	public DbSet<Product> Products { get; set; }          // Represents the 'Products' table
	public DbSet<Basket> Baskets { get; set; }            // Represents the 'Baskets' table
	public DbSet<BasketItem> BasketItem { get; internal set; }  // Represents the 'BasketItem' table

	// Override the 'OnModelCreating' method to configure model relationships and keys
	protected override void OnModelCreating(ModelBuilder modelBuilder)
	{
		// Call the base 'OnModelCreating' method
		base.OnModelCreating(modelBuilder);

		// Configure primary keys for the 'Product' and 'Basket' entities
		modelBuilder.Entity<Product>().HasKey(p => p.Id);
		modelBuilder.Entity<Basket>().HasKey(p => p.Id);

		// Configure a relationship between 'Basket' and 'BasketItem' entities
		modelBuilder.Entity<Basket>().HasMany(x => x.Items)
			.WithOne()
			.HasForeignKey(x => x.BasketId)
			.OnDelete(DeleteBehavior.Cascade);
	}
}