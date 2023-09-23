package com.example.food.pojos;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import lombok.Data;

@Data
public class FavouriteProductResponse extends BaseResponse {
    private int code;
    private String description;
    private ProductDto favouriteProduct;

    public FavouriteProductResponse() {
        this(ResponseCodeEnum.ERROR); // default value
    }

    public FavouriteProductResponse(ResponseCodeEnum responseCode) {
        this.code = responseCode.getCode();
        this.description = responseCode.getDescription();
    }
    public void assignResponseCodeAndDescription(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
