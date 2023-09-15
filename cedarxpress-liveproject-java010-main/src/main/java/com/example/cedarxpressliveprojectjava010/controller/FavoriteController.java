package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.service.FavoriteService;
import com.example.cedarxpressliveprojectjava010.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/addToFavorite/{productId}")
    public ResponseEntity<String> addProductToFavorite(@PathVariable long productId) {
        return favoriteService.addProductToFavorite(productId);
    }

    @DeleteMapping("/deleteFavorite/{productId}/{userId}")
    public ResponseEntity<String> deleteProductFromFavorite
            (@PathVariable(value = "productId") Long productId, @PathVariable(value = "userId") Long userId) {
        return favoriteService.deleteProductFromFavorite(productId, userId);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ViewProductDto> getSingleFavorite(@PathVariable(value = "productId")Long productId){
        return favoriteService.fetchSingleFavoriteProduct(productId);
    }

    @GetMapping()
    public List<ViewProductDto> getAllFavorite(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize){

        return favoriteService.fetchAllFavoriteProduct(pageNo,pageSize);
    }
}
