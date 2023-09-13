﻿namespace EFarm.Server.Model.Dto
{
	public class ProductDto
	{
        public int Id { get; set; }
        public string Name { get; set; }
		public string? Description { get; set; }
		public decimal? OldPrice { get; set; }
		public decimal Price { get; set; }
		public string? PictureUri { get; set; }
	}
}
