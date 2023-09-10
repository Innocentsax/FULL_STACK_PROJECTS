package com.goCash.services;

import com.goCash.dto.request.ElectricityBillQueryRequest;
import com.goCash.dto.request.ElectricityBillRequest;
import com.goCash.dto.response.ElectricityBillResponse;
import com.goCash.utils.ApiResponse;

public interface ElectricityBillService {
    public ApiResponse<ElectricityBillResponse> payElectricityBill(ElectricityBillRequest electricityBillRequest);

    ApiResponse verifyMeterNumber(ElectricityBillQueryRequest requestBody);
}
