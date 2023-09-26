package com.example.money_way.service;

import com.example.money_way.dto.request.*;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.DataVariationsResponse;
import com.example.money_way.dto.response.AccountVerificationResponse;
import com.example.money_way.dto.response.TvPurchaseResponse;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.DataPurchaseResponse;
import com.example.money_way.dto.response.DataVariationsResponse;
import com.example.money_way.dto.request.CableVerificationRequest;
import com.example.money_way.dto.request.ElectricityBillRequest;
import com.example.money_way.dto.response.*;
import com.example.money_way.dto.request.AccountVerificationRequest;
import com.example.money_way.dto.request.AirtimeRequest;
import com.example.money_way.dto.request.DataPurchaseRequest;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.DataVariationsResponse;
import com.example.money_way.dto.request.ElectricityBillRequest;
import com.example.money_way.dto.response.*;



public interface BillService {
    ApiResponse<TvPurchaseResponse> purchaseTvSubscription(CustomerRequestDtoForTvSubscription tvPurchaseRequest);


    ApiResponse buyData(DataPurchaseRequest request);

    AccountVerificationResponse verifyElectricityAccount(AccountVerificationRequest request);

    CableVerificationResponse verifyCableTv(CableVerificationRequest request);

    ApiResponse<DataVariationsResponse> fetchDataVariations(String dataServiceProvider);

    VTPassResponse buyAirtime(AirtimeRequest airtimeRequest);
    ApiResponse payElectricityBill(ElectricityBillRequest request);

    ApiResponse<TvVariationsResponse> fetchTvVariations(String tvServiceProvider);
}
