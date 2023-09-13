using EFarm.Server.Model;

namespace EFarm.Server.Model
{
	// Define the namespace for the model classes

	// Define a class named 'Product' that inherits from 'BaseEntity'
	public class Product : BaseEntity
	{
		// Define public properties to store information about a product
		public string Name { get; set; }          // Name of the product
		public string? Description { get; set; }   // Description of the product (nullable)
		public decimal? OldPrice { get; set; }     // Previous price of the product (nullable)
		public decimal Price { get; set; }         // Current price of the product
		public string? PictureUri { get; set; }    // URI (path) to the product's picture (nullable)

		// Default constructor for 'Product'
		public Product()
		{
			// This constructor is empty and doesn't perform any specific initialization.
		}

		// Constructor for 'Product' that receives 'name', 'oldPrice', and 'price' as parameters
		public Product(string name, decimal? oldPrice, decimal price)
		{
			// Initialize the properties with the provided values
			Name = name;
			OldPrice = oldPrice;
			Price = price;
		}

		// Constructor for 'Product' that receives 'name', 'description', 'price', and 'pictureUri' as parameters
		public Product(string name, string description, decimal price, string pictureUri)
		{
			// Initialize the properties with the provided values
			Name = name;
			Description = description;
			Price = price;
			PictureUri = pictureUri;
		}

		// Constructor for 'Product' that receives 'name', 'description', 'oldPrice', 'price', and 'pictureUri' as parameters
		public Product(string name, string description, decimal oldPrice, decimal price, string pictureUri)
		{
			// Initialize the properties with the provided values
			Name = name;
			Description = description;
			Price = price;
			OldPrice = oldPrice;
			PictureUri = pictureUri;
		}

		// Constructor for 'Product' that receives 'name', 'oldPrice', 'price', and 'pictureUri' as parameters
		public Product(string name, decimal oldPrice, decimal price, string pictureUri)
		{
			// Initialize the properties with the provided values
			Name = name;
			OldPrice = oldPrice;
			Price = price;
			PictureUri = pictureUri;
		}
	}
}