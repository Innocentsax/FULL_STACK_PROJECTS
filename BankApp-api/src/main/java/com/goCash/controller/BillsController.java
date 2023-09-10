package com.goCash.controller;

import com.goCash.dto.request.AirtimePurchaseRequest;
import com.goCash.dto.request.ElectricityBillQueryRequest;
import com.goCash.dto.request.ElectricityBillRequest;
import com.goCash.services.AirtimePurchaseServices;
import com.goCash.dto.request.DataSubscriptionRequest;
import com.goCash.services.DataService;

import com.goCash.services.ElectricityBillService;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/go-cash/paybill")
@RequiredArgsConstructor
public class BillsController {

    private final ElectricityBillService electricityBillService;
    private final AirtimePurchaseServices airtimePurchaseServices;
    private final DataService dataService;


    @PostMapping("/electricity/pay")
    public ResponseEntity<ApiResponse> payElectricityBill(@RequestBody ElectricityBillRequest electricityBillRequest) {
        log.info("request to pay for electricity units.");
        ApiResponse transactionStatusResponse = electricityBillService.payElectricityBill(electricityBillRequest);
        return ResponseEntity.ok(transactionStatusResponse);
    }

    @PostMapping(path = "/airtime")
    public ResponseEntity<?> viewTvVariations(@RequestBody AirtimePurchaseRequest request) {
        log.info("request to purchase airTime activated.");
        ApiResponse response = airtimePurchaseServices.purchaseAirtime(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase-data")
    public ResponseEntity<ApiResponse> purchaseData(@Valid @RequestBody DataSubscriptionRequest request) {

        ApiResponse response = dataService.purchaseDataBundle(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-disco")
    public ResponseEntity<ApiResponse> verifyMeterNumber(@RequestBody ElectricityBillQueryRequest electricityBillQueryRequest) {
        ApiResponse response = electricityBillService.verifyMeterNumber(electricityBillQueryRequest);
        return ResponseEntity.ok(response);
    }
}
