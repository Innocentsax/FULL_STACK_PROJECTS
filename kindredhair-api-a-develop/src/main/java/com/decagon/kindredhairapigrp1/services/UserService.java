package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.DTO.UserDTO;
import com.decagon.kindredhairapigrp1.models.ProductRecommendation;
import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    ResponseEntity<ApiResponse<Object>> getUserProfileDetails(Long id);

    ApiResponse registerUser(UserDTO userDTO, AnswerResponseDTO answerResponseDto);

    List<ProductRecommendation> generateUserProductRecommendation(AnswerResponseDTO answerResponseDTO, long userDetailsId);

    ApiResponse<Map<String, List<ProductDTO>>> getUserRecommendedProduct(Long userId);

    ApiResponse<Object> updateUserRecommendation(long userId, AnswerResponseDTO answerResponseDTO);

}