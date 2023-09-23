package com.example.food.services;

import com.example.food.model.Product;
import com.example.food.pojos.FavouriteProductResponse;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface FavouritesService {
    public BaseResponse addToFavourites(Long productId);

    public BaseResponse removeFromFavourites (Long favouriteId);

    FavouriteProductResponse viewAFavouriteProduct(Long favouriteId);
}
