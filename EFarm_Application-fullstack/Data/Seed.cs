using EFarm.Server.Model;

namespace EFarm.Server.Data;

public class Seed
{

	public static async Task Initialise(AppDbContext context)
	{
		if (context.Products.Any()) return;
		var products = new List<Product> {
            new Product("Smoked Meat ", "Smoked Meat ", 98.99m, 48.99m, "https://food-market.cmsmasters.net/wp-content/uploads/2015/05/15.jpg"),
            new( "Green Apple", "Green Apple", 19.5M, "/images/greenapple.jpg"),
            new( "Cashew Nuts", "Cashew Nuts",15m, 12, "/images/cashew.png"),
            new( "Onion", "Onion", 12, "/images/onions.jpg"),
            new( "Cabbage", "Cabbage", 8.50M, "/images/cabbage-1.jpg"),
            new( "Cocoa Seed", "Cocoa Seed", 12, "/images/cocoa.png"),
            new( "Egg", "Egg", 8.5M, "/images/eggs-1-360x360.jpg"),
            new("Basket of Apple", "Basket of Apple", 12, "/images/apples.jpg"),
            new("Red Pepper", "Red Pepper", 12, "/images/pepper-red-1-360x360.jpg"),
            new( "Vegetables", "Vegetables", 12, "/images/veg.jpg"),
            new( "Tomatoes", "Tomatoes", 8.5M, "/images/tomatoes.jpg"),
            new("Ofada Rice", "Ofada Rice", 12, "/images/rice.png")
};
		products.ForEach(p => context.Products.Add(p));
		await context.SaveChangesAsync();
	}
}
