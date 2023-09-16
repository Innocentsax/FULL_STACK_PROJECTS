export default class Product {
  constructor(
    categoryName,
    productName,
    productPrice,
    quantity,
    size,
    productDescription,
    productId,
  ) {
    this.categoryName = categoryName;
    this.productName = productName;
    this.productPrice = productPrice;
    this.quantity = quantity;
    this.size = size;
    this.productDescription = productDescription;
    this.productId = productId;
  }
}

