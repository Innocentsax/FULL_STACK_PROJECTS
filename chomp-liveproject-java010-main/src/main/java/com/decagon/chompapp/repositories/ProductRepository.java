package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " + "LOWER(" + "CONCAT(p.productId, p.productName, p.quantity, p.productImage, p.productPrice,p.category.categoryName))" + "LIKE %?1%")
    Page<Product> findAllByFilterParam (Pageable pageable, String filterParam);

    @Query("SELECT p FROM Product p WHERE " + "LOWER(" + "p.productName)" + "LIKE %?1%")
    Page<Product> findAllByProductNameContains (Pageable pageable, String productName);

    @Query("SELECT p FROM Product p WHERE " + "LOWER(" + "p.size)" + "LIKE %?1%")
    Page<Product> findAllBySizeContains (Pageable pageable, String size);

    @Query("SELECT p FROM Product  p WHERE :filterParam BETWEEN :startRange AND :endRange")
    Page<Product> findAllByProductPriceBetween (Pageable pageable, double filterParam, double startRange,double endRange);

    @Query("SELECT p FROM Product p WHERE " + "LOWER(" + "p.category.categoryName)" + "=:categoryName")
    Page<Product> findAllByCategory_CategoryName(Pageable pageable, @Param("categoryName") String categoryName);

    Optional<Product> findProductByProductId(Long productId);

    @Query(value = "SELECT p FROM Product p JOIN Favorites f ON p.productId = f.product.productId WHERE f.user.userId = ?1")
    List<Product> findAllFavoriteProductsByUserId(Long userId);
}
