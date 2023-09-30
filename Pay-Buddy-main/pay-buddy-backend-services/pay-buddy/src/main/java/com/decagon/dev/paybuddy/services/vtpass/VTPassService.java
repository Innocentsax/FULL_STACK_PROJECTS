package com.decagon.dev.paybuddy.services.vtpass;

import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyAirtimeRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyDataPlanRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyElectricityRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.VerifyMerchantRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.BuyDataPlanResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.DataPlansResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.DataServicesResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.BuyElectricityResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.VerifyMerchantResponse;

import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.*;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 19:28
 * @project Pay-Buddy
 */

public interface VTPassService {
    DataServicesResponse getDataServices();
    DataPlansResponse getDataPlans(String dataType);
    BuyDataPlanResponse payDataPlan(BuyDataPlanRequest request);

    DataServicesResponse getAllElectricityService();

    VerifyMerchantResponse verifyElectricityMeter(VerifyMerchantRequest merchantRequest);

    BuyElectricityResponse buyElectricity(BuyElectricityRequest electricityRequest);



    BuyAirtimeResponse buyAirtime(BuyAirtimeRequest buyAirtimeRequest);

    AirtimeServiceResponse getAirtimeServices();
}
