package com.decagon.chompapp.services;

import org.springframework.http.ResponseEntity;

public interface FavoritesService {

    ResponseEntity<String> addProductToFavorite (Long productId);
    ResponseEntity<String> removeProductFromFavorite (Long productId);

}