package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
