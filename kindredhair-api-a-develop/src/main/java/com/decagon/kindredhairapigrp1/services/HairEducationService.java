package com.decagon.kindredhairapigrp1.services;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface HairEducationService {
    ResponseEntity<ApiResponse<Object>> getUserHairEducation(Long userDetailsID);
}
