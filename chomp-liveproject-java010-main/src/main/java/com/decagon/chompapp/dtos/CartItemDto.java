package com.decagon.chompapp.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private long id;
    private long productId;
    private String productName;
    private String productImage;
    private String productSize;
    private int quantity;
    private double unitPrice;
    private double subTotal;
    private long cartId;

    /*Product image, Product name, Product size, Product quantity, Unit price , SubTotal.
     */
}
