export default class Cart {
  constructor(
    userId,
    productId,
    productName,
    productImage,
    unitPrice,
    
  ) {
    this.userId = userId;
    this.productId = productId;
    this.productName = productName;
    this.productImage = productImage;
    this.unitPrice = unitPrice;
  }
}