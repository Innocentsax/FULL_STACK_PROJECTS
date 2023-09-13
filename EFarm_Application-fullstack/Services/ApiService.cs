using EFarm.Server.Model.Dto;
using Microsoft.AspNetCore.WebUtilities;
using System.Text;

namespace EFarm.Server.Services
{
    public class ApiService:IApiService
    {
        private readonly HttpClient _httpClient;
        private readonly IHttpContextAccessor _httpContextAccessor;
        public ApiService(HttpClient httpClient, IHttpContextAccessor httpContextAccessor)
        {
            _httpClient = httpClient;
            _httpContextAccessor = httpContextAccessor;
            Init();
        }

        private void Init()
        {
            _httpClient.DefaultRequestHeaders.Add("Accept", "application/json");
            if (_httpContextAccessor.HttpContext != null && _httpContextAccessor.HttpContext.Request!=null)
            {
                var sessionUser = _httpContextAccessor.HttpContext.Request.Cookies["user"];
                if(sessionUser != null)
                {
                    //var userId=  Encoding.UTF8.GetString(sessionUser);
                    var userHeader = _httpClient.DefaultRequestHeaders.FirstOrDefault(x => x.Key == "userId");
                    if(userHeader.Key!="userId")
				        _httpClient.DefaultRequestHeaders.Add("userId", sessionUser);
				    }

			}

		}

        public async Task<IEnumerable<ProductDto>> GetProducts(string q = "")
        {
            var uri = "products";
            

			var response =  await _httpClient.GetFromJsonAsync<IEnumerable<ProductDto>>(uri);
            if (!string.IsNullOrEmpty(q))
            {
                response=response.Where(x=>x.Name.ToLower().Contains(q.ToLower()));
                //var query = new Dictionary<string, string>()
                //{
                //    ["q"] = q
                //};
                //uri = QueryHelpers.AddQueryString(uri, query);
            }
            return response;
        }

		public async Task<bool> AddToBasket(BasketItemDto basketItemDto)
		{
			var response = await _httpClient.PostAsJsonAsync("basket/add", basketItemDto);
			return response.IsSuccessStatusCode;
		}
		public async Task<BasketDto> GetBasket()
		{
            var response= await _httpClient.GetFromJsonAsync<BasketDto>("basket");
            return response;
		}

		public async Task<ProductDto> GetProduct(int id)
		{
			var response = await _httpClient.GetFromJsonAsync<ProductDto>($"products/{id}");
			return response;
		}

        public async Task<bool> RemoveItem(int basketItemId)
        {
            var response = await _httpClient.DeleteAsync($"basket/remove/{basketItemId}");
            return response.IsSuccessStatusCode;
        }
        public async Task<bool> UpdateQuantity(BasketItemDto item)
        {
            var response = await _httpClient.PutAsJsonAsync($"basket/quantity", item);
            return response.IsSuccessStatusCode;
        }

        public async Task<int> CountTotalBasketItems()
        {
            Init();
            return await _httpClient.GetFromJsonAsync<int>("basket/count");
        }
        public async Task<bool> ClearBasket()
        {
            var response= await _httpClient.PostAsync("basket/checkout",null);
           
            return response.IsSuccessStatusCode;
        }
    }
}


