using EFarm.Server.Model.Dto;

namespace EFarm.Server.Services
{
	public interface IApiService
	{
		Task<IEnumerable<ProductDto>> GetProducts(string q="");
		Task<bool> AddToBasket(BasketItemDto basketItemDto);
		Task<ProductDto> GetProduct(int id);
		Task<BasketDto> GetBasket();
		Task<bool> RemoveItem(int basketItemId);
        Task<bool> UpdateQuantity(BasketItemDto item);
        Task<int> CountTotalBasketItems();
        Task<bool> ClearBasket();
    }
}