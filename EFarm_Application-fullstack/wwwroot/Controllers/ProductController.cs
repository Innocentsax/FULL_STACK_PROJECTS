using Efarm.App.Data;
using Efarm.App.Model;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// Import necessary libraries for this controller

// Define the controller within the 'Efarm.App.Controllers' namespace
namespace Efarm.App.Controllers
{
	// Define the route for this controller, making it accessible at '/api/products'
	[Route("api/products")]

	// Indicate that this class is a controller for handling HTTP requests
	[ApiController]

	// Create a class named 'ProductController' that inherits from 'ControllerBase'
	public class ProductController : ControllerBase
	{
		// Define a private field for the database context
		readonly AppDbContext _dbContext;

		// Constructor for 'ProductController' that receives 'AppDbContext' instance
		public ProductController(AppDbContext dbContext)
		{
			// Initialize the private field with the provided 'AppDbContext' instance
			_dbContext = dbContext;
		}

		// Define an HTTP GET method to retrieve a list of products
		[HttpGet]
		public async Task<List<Product>> Get()
		{
			// Retrieve all products from the database asynchronously and convert them to a list
			var products = await _dbContext.Products.ToListAsync();

			// Uncomment the following lines if you want to filter products based on a search query (q)
			// if (!string.IsNullOrWhiteSpace(q))
			// {
			//     products = products.Where(x => x.Name.ToLower().Contains(q.ToLower())).ToList();
			// }

			// Return the list of products
			return products;
		}

		// Define an HTTP GET method to retrieve a product by its ID
		[HttpGet("{id}")]
		public async Task<Product> Get(int id)
		{
			// Find and return a product with the specified ID from the database
			return await _dbContext.Products.FindAsync(id);
		}
	}
}
