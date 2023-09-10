package com.goCash.services;

import com.goCash.dto.request.DataSubscriptionRequest;
import com.goCash.utils.ApiResponse;

import javax.transaction.Transactional;

public interface DataService {
    ApiResponse getDataVariation(String provider);

    @Transactional
    ApiResponse purchaseDataBundle(DataSubscriptionRequest request);
}
