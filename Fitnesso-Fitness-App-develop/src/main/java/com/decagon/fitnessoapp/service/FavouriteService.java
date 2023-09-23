package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FavouriteService {
    ResponseEntity<String> addOrDeleteFavourite(Long productId, Authentication authentication);

    Boolean handleFavourite(String username, Long productId);

    Boolean checkFaveDefault(String username, Long productId);

    List<ProductResponseDto> viewFavourites(Authentication authentication);
}
