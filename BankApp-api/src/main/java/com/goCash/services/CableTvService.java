package com.goCash.services;

import com.goCash.dto.request.TvSubscriptionRequest;
import com.goCash.dto.request.VerifyCableTvRequest;
import com.goCash.utils.ApiResponse;

import javax.transaction.Transactional;


public interface CableTvService {
    ApiResponse getTvProviderBouquets(String provider);

    ApiResponse queryCableTvSubscription(String cableTvRequest);

    @Transactional
    ApiResponse purchaseTvSubscription(TvSubscriptionRequest request);

    ApiResponse verifySmartcardNumber(VerifyCableTvRequest verifyCableTvRequest);
}
