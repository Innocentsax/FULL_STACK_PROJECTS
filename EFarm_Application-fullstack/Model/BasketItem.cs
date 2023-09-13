using EFarm.Server.Model;

namespace EFarm.Server.Model
{
	// Define the namespace for the model classes

	// Define a class named 'BasketItem' that inherits from 'BaseEntity'
	public class BasketItem : BaseEntity
	{
		// Define public properties to store information about a basket item
		public decimal UnitPrice { get; set; }  // Price of one unit of the product
		public int Quantity { get; set; }       // Quantity of the product in the basket
		public int ProductId { get; set; }      // Identifier of the associated product
		public int BasketId { get; set; }       // Identifier of the basket to which the item belongs

		// Define a nullable 'Product' property to represent additional product information
		public Product? Product { get; set; }

		// Default constructor for 'BasketItem'
		public BasketItem()
		{
			// This constructor is empty and doesn't perform any specific initialization.
		}

		// Constructor for 'BasketItem' that receives 'productId', 'quantity', and 'unitPrice' as parameters
		public BasketItem(int productId, int quantity, decimal unitPrice)
		{
			// Initialize the properties with the provided values
			ProductId = productId;
			UnitPrice = unitPrice;
			SetQuantity(quantity);  // Call the 'SetQuantity' method to set the quantity
		}

		// Method to add a specified quantity to the current quantity of the basket item
		public void AddQuantity(int quantity)
		{
			Quantity += quantity;
		}

		// Method to set the quantity of the basket item to a specified value
		public void SetQuantity(int quantity)
		{
			Quantity = quantity;
		}
	}
}


