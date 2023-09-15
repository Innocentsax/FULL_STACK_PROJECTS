package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface FavoriteService {
    ResponseEntity<String> addProductToFavorite(Long productId);

    ResponseEntity<String>deleteProductFromFavorite(Long productId, Long userId);
    List<ViewProductDto> fetchAllFavoriteProduct(int pageNo, int pageSize);
    ResponseEntity<ViewProductDto> fetchSingleFavoriteProduct(Long productId);
}
