package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.DTO.ProductPriorityDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProductRecommendationService {
    Map<String, List<ProductDTO>> getProductRecommendation(Long userId) throws Exception;
    List<ProductPriorityDTO> generatePriorityList(String describe, String allergies, String priority,
                                                         String whatElse, String porosity, String goals,
                                                         String styles, int rating, String brandsIUse,
                                                         String brandsIDontLike, String budget, String type);
    String getProductRecommendationCategory(String type, String answer);
    List<List<ProductDTO>> populateRecommendationLists(
            List<List<ProductPriorityDTO>> superListOfProductLists, String answer);
}
