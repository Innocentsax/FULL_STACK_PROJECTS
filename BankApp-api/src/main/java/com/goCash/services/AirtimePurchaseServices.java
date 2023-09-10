package com.goCash.services;

import com.goCash.dto.request.AirtimePurchaseRequest;
import com.goCash.utils.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public interface AirtimePurchaseServices {

    ApiResponse purchaseAirtime(AirtimePurchaseRequest request);
}
