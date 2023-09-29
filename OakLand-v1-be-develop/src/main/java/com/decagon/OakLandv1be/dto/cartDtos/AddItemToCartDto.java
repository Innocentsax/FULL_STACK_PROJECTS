package com.decagon.OakLandv1be.dto.cartDtos;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddItemToCartDto {

    @NotNull(message = "Kindly specify the quantity you desire, for this product")
    private Integer orderQty;
}
