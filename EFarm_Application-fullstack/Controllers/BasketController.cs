using EFarm.Server.Data;
using EFarm.Server.Model;
using EFarm.Server.Model.Dto;
using EFarm.Server.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// Import necessary libraries for this controller

// Define the controller within the 'EFarm.Server.Controllers' namespace
namespace EFarm.Server.Controllers
{
	// Define the route for this controller, making it accessible at '/api/basket'
	[Route("api/basket")]

	// Indicate that this class is a controller for handling HTTP requests
	[ApiController]

	// Create a class named 'BasketController' that inherits from 'ControllerBase'
	public class BasketController : ControllerBase
	{
		// Define private fields for database context and session management
		readonly AppDbContext _dbContext;
		IAppSession _session;

		// Constructor for 'BasketController' that receives 'AppDbContext' and 'IAppSession' instances
		public BasketController(AppDbContext dbContext, IAppSession session)
		{
			// Initialize the private fields with the provided instances
			_dbContext = dbContext;
			_session = session;
		}

		// Define an HTTP GET method to retrieve the user's shopping basket
		[HttpGet]
		public async Task<BasketDto> Get()
		{
			// Retrieve or create a user's shopping basket
			var entity = await GetOrCreateBasketForUser();

			// Create a DTO (Data Transfer Object) to represent the shopping basket
			var basket = new BasketDto
			{
				Id = entity.Id,
				BuyerId = entity.BuyerId,
			};

			// Populate the DTO with items in the shopping basket
			foreach (var item in entity.Items)
			{
				basket.Items.Add(new BasketItemDto
				{
					Id = item.Id,
					Quantity = item.Quantity,
					UnitPrice = item.UnitPrice,
					Product = new ProductDto
					{
						Name = item.Product?.Name,
						PictureUri = item.Product?.PictureUri,
					}
				});
			}

			// Return the DTO representing the shopping basket
			return basket;
		}

		// Define an HTTP GET method to count the number of items in the shopping basket
		[HttpGet("count")]
		public async Task<int> CountBasketItems()
		{
			// Retrieve the user's shopping basket and calculate the total number of unique items
			var basket = await _dbContext.Baskets.Include(x => x.Items)
				.FirstOrDefaultAsync(basket => basket.BuyerId == _session.UserId);
			var totalItems = basket == null ? 0 : basket.Items.GroupBy(x => x.ProductId).Count();

			// Return the total item count
			return totalItems;
		}

		// Define an HTTP POST method to add an item to the shopping basket
		[HttpPost("add")]
		public async Task AddItem([FromBody] BasketItemDto item)
		{
			// Retrieve or create the user's shopping basket
			var basket = await GetOrCreateBasketForUser();

			// Retrieve items in the shopping basket
			var items = await _dbContext.BasketItem.Where(x => x.BasketId == basket.Id).ToListAsync();

			// Check if the item to be added already exists in the basket
			if (!items.Any(i => i.ProductId == item.ProductId))
			{
				// If not, add a new item to the basket
				_dbContext.BasketItem.Add(new BasketItem(item.ProductId, item.Quantity, item.UnitPrice) { BasketId = basket.Id });
			}
			else
			{
				// If the item exists, update its quantity
				var existingItem = items.First(i => i.ProductId == item.ProductId);
				existingItem.AddQuantity(item.Quantity);
				_dbContext.BasketItem.Update(existingItem);
			}

			// Save changes to the database
			await _dbContext.SaveChangesAsync();
		}

		// Define an HTTP DELETE method to remove an item from the shopping basket
		[HttpDelete("remove/{id}")]
		public async Task RemoveItem([FromRoute] int id)
		{
			// Find the user's shopping basket
			var basket = await _dbContext.Baskets.FirstOrDefaultAsync(x => x.BuyerId == _session.UserId);

			// If the basket exists, find and remove the specified item
			if (basket is not null)
			{
				var item = await _dbContext.BasketItem.FirstOrDefaultAsync(x => x.Id == id && x.BasketId == basket.Id);
				if (item is not null)
				{
					_dbContext.Remove(item);
					await _dbContext.SaveChangesAsync();
				}
			}
		}

		// Define an HTTP PUT method to update the quantity of an item in the shopping basket
		[HttpPut("quantity")]
		public async Task UpdateQuantity([FromBody] BasketItemDto item)
		{
			// Find the user's shopping basket
			var basket = await _dbContext.Baskets.FirstOrDefaultAsync(x => x.BuyerId == _session.UserId);

			// If the basket exists, find and update the specified item's quantity
			if (basket is not null)
			{
				var basketItem = await _dbContext.BasketItem.FirstOrDefaultAsync(x => x.Id == item.Id &&
				   x.BasketId == basket.Id);
				if (basketItem is not null)
				{
					basketItem.Quantity = item.Quantity;
					_dbContext.Update(basketItem);
					await _dbContext.SaveChangesAsync();
				}
			}
		}

		// Define an HTTP POST method to clear the user's shopping basket
		[HttpPost("checkout")]
		public async Task ClearBasket()
		{
			// Find the user's shopping basket and remove all items
			var basket = await _dbContext.Baskets.Include(x => x.Items).FirstOrDefaultAsync(x => x.BuyerId == _session.UserId);
			if (basket is not null)
			{
				_dbContext.BasketItem.RemoveRange(basket.Items);
				_dbContext.Baskets.Remove(basket);
				await _dbContext.SaveChangesAsync();
			}
		}

		// Helper method to retrieve or create the user's shopping basket
		private async Task<Basket> GetOrCreateBasketForUser()
		{
			var userId = _session.UserId;
			if (string.IsNullOrEmpty(userId))
				throw new Exception("No User found.");

			var basket = await GetUserBasket(userId);

			if (basket == null)
			{
				basket = await CreateBasketForUser(userId);
			}
			return basket;
		}

		// Helper method to create a new shopping basket for a user
		private async Task<Basket> CreateBasketForUser(string userId)
		{
			var basket = new Basket(userId);
			await _dbContext.Baskets.AddAsync(basket);
			await _dbContext.SaveChangesAsync();

			return basket;
		}

		// Helper method to retrieve a user's shopping basket from the database
		private async Task<Basket> GetUserBasket(string userId)
		{
			var basket = await _dbContext.Baskets.Include(x => x.Items).ThenInclude(x => x.Product).FirstOrDefaultAsync(x => x.BuyerId == userId);
			return basket;
		}
	}
}