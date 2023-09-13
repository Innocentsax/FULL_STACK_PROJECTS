namespace EFarm.Server.Model.Dto
{
	// Define a namespace for Data Transfer Objects (DTOs)

	// Define a class named 'BasketItemDto' to represent a DTO for basket items
	public class BasketItemDto
	{
		// Define public properties to store information about a basket item
		public int Id { get; set; }            // Unique identifier for the basket item
		public int ProductId { get; set; }     // Identifier of the associated product
		public decimal UnitPrice { get; set; } // Price of one unit of the product
		public int Quantity { get; set; }      // Quantity of the product in the basket

		// Define a nullable 'ProductDto' property to represent additional product information
		public ProductDto? Product { get; set; }

		// Define a calculated property 'SubTotal' to calculate the total cost of the basket item
		public decimal SubTotal => Quantity * UnitPrice;
	}
}
