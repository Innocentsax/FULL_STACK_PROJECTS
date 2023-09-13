using EFarm.Server.Model;

namespace EFarm.Server.Model
{
	// Define a class named 'Basket' that inherits from 'BaseEntity'
	public class Basket : BaseEntity
    {
		// Define a public property 'BuyerId' to store the identifier of the buyer
		public string BuyerId { get; set; }
		// Define a public property 'Items' to store a list of 'BasketItem' objects, initialized as an empty list
		public List<BasketItem> Items { get; set; } = new();
		// Define a calculated property 'TotalItems' to compute the total number of items in the basket
		public int TotalItems => Items.Sum(i => i.Quantity);

		// Constructor for 'Basket' that receives a 'buyerId' as a parameter
		public Basket(string buyerId)
        {
			// Initialize the 'BuyerId' property with the provided 'buyerId'
			BuyerId = buyerId;
        }

        
    }

}
