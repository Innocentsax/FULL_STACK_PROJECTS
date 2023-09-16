package com.decagon.chompapp.controllers;

import com.decagon.chompapp.services.FavoritesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoritesService favoritesService;

    @PostMapping("/{productId}/add")
    public ResponseEntity<?> addProductToFavorite (
            @PathVariable(value = "productId") Long productId
    ) {
        return favoritesService.addProductToFavorite(productId);
    }

    @DeleteMapping("/{productId}/remove")
    public ResponseEntity<?> removeProductFromFavorite (
            @PathVariable(value = "productId") Long productId
    ) {
        return favoritesService.removeProductFromFavorite(productId);
    }

}
