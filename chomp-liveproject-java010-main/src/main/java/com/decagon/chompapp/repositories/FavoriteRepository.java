package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Favorites;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    Boolean existsByUserAndProduct (User user, Product product);
    Optional<Favorites> findFavoritesByProduct_ProductIdAndUser_UserId (Long productId, Long userId);
}