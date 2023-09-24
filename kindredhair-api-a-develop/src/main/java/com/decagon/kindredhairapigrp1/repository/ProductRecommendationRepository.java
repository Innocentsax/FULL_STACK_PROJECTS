package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.ProductRecommendation;
import com.decagon.kindredhairapigrp1.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {
    List<ProductRecommendation> findProductRecommendationByUserDetails(UserDetails userDetails);
}
