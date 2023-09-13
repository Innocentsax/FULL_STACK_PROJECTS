namespace EFarm.Server.Model.Dto
{
	public class BasketDto
	{
		public int Id { get; set; }
        public string BuyerId { get; set; }
		public List<BasketItemDto> Items { get; set; } = new();
		public decimal Total => Items.Sum(x => x.SubTotal);
    }
}
