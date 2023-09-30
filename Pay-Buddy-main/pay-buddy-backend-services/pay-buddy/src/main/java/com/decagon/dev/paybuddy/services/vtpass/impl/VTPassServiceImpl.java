package com.decagon.dev.paybuddy.services.vtpass.impl;

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
import com.decagon.dev.paybuddy.services.vtpass.VTPassService;
import com.decagon.dev.paybuddy.utilities.AppUtil;
import com.decagon.dev.paybuddy.utilities.VTPassHttpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import static com.decagon.dev.paybuddy.utilities.VTPassConstants.*;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 10:32
 * @project Pay-Buddy
 */

@Service
@RequiredArgsConstructor
public class VTPassServiceImpl implements VTPassService {
    private final VTPassHttpEntity<? super Object> vtPassHttpEntity;
    private final RestTemplate restTemplate;
    private final AppUtil util;

    @Override
    public DataServicesResponse getDataServices() {
        return restTemplate.exchange(
                ALL_DATA_SERVICES,
                HttpMethod.GET,
                vtPassHttpEntity.getEntity(null),
                DataServicesResponse.class
        ).getBody();
    }

    @Override
    public DataPlansResponse getDataPlans(String dataType) {
        return restTemplate.exchange(
                PAY_BILL_SERVICE + dataType,
                HttpMethod.GET,
                vtPassHttpEntity.getEntity(null),
                DataPlansResponse.class
        ).getBody();
    }

    @Override
    public BuyDataPlanResponse payDataPlan(BuyDataPlanRequest request) {
        request.setBillersCode(BILLER_CODE);
        request.setRequest_id(getRequestId());

        return restTemplate.exchange(
                PAY_BILL,
                HttpMethod.POST,
                vtPassHttpEntity.getEntity(request),
                BuyDataPlanResponse.class
        ).getBody();
    }

    @Override
    public BuyAirtimeResponse buyAirtime(BuyAirtimeRequest buyAirtimeRequest) {
        buyAirtimeRequest.setRequest_id(getRequestId());
        return restTemplate.exchange(
                PAY_BILL,
                HttpMethod.POST,
                vtPassHttpEntity.getEntity(buyAirtimeRequest),
                BuyAirtimeResponse.class
        ).getBody();
    }

    @Override
    public DataServicesResponse getAllElectricityService() {
        return restTemplate
                .exchange(
                        ALL_ELECTRICITY_SERVICES,
                        HttpMethod.GET,
                        vtPassHttpEntity.getEntity(null),
                        DataServicesResponse.class
                ).getBody();
    }

    @Override
    public VerifyMerchantResponse verifyElectricityMeter(VerifyMerchantRequest merchantRequest) {
        return restTemplate
                .exchange(
                        VERIFY_MERCHANT,
                        HttpMethod.POST,
                        vtPassHttpEntity.getEntity(merchantRequest),
                        VerifyMerchantResponse.class
                ).getBody();

    }

    @Override
    public AirtimeServiceResponse getAirtimeServices() {
        return restTemplate.exchange(
                All_AIRTIME_SERVICES,
                HttpMethod.GET,
                vtPassHttpEntity.getEntity(null),
                AirtimeServiceResponse.class
        ).getBody();

    }

    @Override
    public BuyElectricityResponse buyElectricity(BuyElectricityRequest electricityRequest) {
        electricityRequest.setRequest_id(getRequestId());

        return restTemplate.exchange(
                        PAY_BILL,
                        HttpMethod.POST,
                        vtPassHttpEntity.getEntity(electricityRequest),
                        BuyElectricityResponse.class
                ).getBody();
    }

    /**
     * <a href="https://www.vtpass.com/documentation/how-to-generate-request-id/">...</a>
     */

    private String getRequestId() { //edge cases taking care off
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        return String.valueOf(cal.get(Calendar.YEAR)) +
                (String.valueOf(cal.get(Calendar.MONTH) + 1).length() == 1
                        ? "0" + (cal.get(Calendar.MONTH) + 1) : (cal.get(Calendar.MONTH) + 1)) +
                (String.valueOf(cal.get(Calendar.DAY_OF_MONTH)).length() == 1
                        ? "0" + (cal.get(Calendar.DAY_OF_MONTH)) : cal.get(Calendar.DAY_OF_MONTH)) +
                (String.valueOf(cal.get(Calendar.HOUR_OF_DAY)).length() == 1
                        ? "0" + (cal.get(Calendar.HOUR_OF_DAY)) : cal.get(Calendar.HOUR_OF_DAY)) +
                (String.valueOf(cal.get(Calendar.MINUTE)).length() == 1
                        ? "0" + (cal.get(Calendar.MINUTE)) : cal.get(Calendar.MINUTE)) +
                util.generateSerialNumber("D");
    }

}

