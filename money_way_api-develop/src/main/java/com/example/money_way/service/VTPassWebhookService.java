package com.example.money_way.service;

import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.VTPassApiResponse;
import com.example.money_way.dto.webhook.VTPassWebhookResponse;
import org.springframework.http.ResponseEntity;

public interface VTPassWebhookService {
    ResponseEntity<VTPassWebhookResponse> billsWebhookHandler(VTPassApiResponse vtPassApiResponse);
}
