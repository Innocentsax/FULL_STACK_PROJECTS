package com.decagon.dev.paybuddy.services.vtpass.impl;

import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyAirtimeRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyDataPlanRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.*;
import com.decagon.dev.paybuddy.services.vtpass.VTPassService;
import com.decagon.dev.paybuddy.utilities.AppUtil;
import com.decagon.dev.paybuddy.utilities.VTPassConstants;
import com.decagon.dev.paybuddy.utilities.VTPassHttpEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Ikechi Ucheagwu
 * @created 09/03/2023 - 01:54
 * @project Pay-Buddy
 */

@ExtendWith(MockitoExtension.class)
class  VTPassServiceImplTest {

    private VTPassService vtPassService;

    @Mock
    private VTPassHttpEntity<? super  Object> vtPassHttpEntity;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppUtil util;

    @BeforeEach
    public void init() {
        vtPassService = new VTPassServiceImpl(vtPassHttpEntity, restTemplate, util);
    }

    @Test
    void getDataServices() {
        DataServices dataServices = DataServices.builder()
                .serviceID("mtn-data")
                .name("MTN")
                .image("htpp://anexample.com/image")
                .build();

        DataServicesResponse dataServicesResponse = DataServicesResponse.builder()
                .response_description("0000")
                .content(new ArrayList<>(Collections.singletonList(dataServices)))
                .build();

        when(restTemplate.exchange(
                VTPassConstants.ALL_DATA_SERVICES,
                HttpMethod.GET,
                vtPassHttpEntity.getEntity(null),
                DataServicesResponse.class
        )).thenReturn(new ResponseEntity<>(dataServicesResponse, HttpStatus.OK));

        DataServicesResponse response = vtPassService.getDataServices();
        assertEquals(dataServicesResponse, response);
    }

    @Test
    void getDataPlans() {
        Varation varation = Varation.builder()
                .variation_code("mtn-10mb-100")
                .name("N100 100MB - 24 hrs")
                .variation_amount("100.00")
                .fixedPrice("Yes")
                .build();

        Content content = Content.builder()
                .ServiceName("MTN Data")
                .serviceID("mtn-data")
                .convinience_fee("0 %")
                .varations(new ArrayList<>(Collections.singletonList(varation)))
                .build();

        DataPlansResponse dataPlansResponse = DataPlansResponse.builder()
                .response_description("0000")
                .content(content)
                .build();

        when(restTemplate.exchange(
                VTPassConstants.PAY_BILL_SERVICE + "mtn-data",
                HttpMethod.GET,
                vtPassHttpEntity.getEntity(null),
                DataPlansResponse.class
        )).thenReturn(new ResponseEntity<>(dataPlansResponse, HttpStatus.OK));

        DataPlansResponse response = vtPassService.getDataPlans("mtn-data");
        assertEquals(dataPlansResponse, response);
    }

    @Test
    void payDataPlan() {
        BuyDataPlanRequest request = BuyDataPlanRequest.builder()
                .request_id("202303090250D12985346")
                .serviceID("mtn-data")
                .variation_code("mtn-10mb-100")
                .amount(new BigDecimal("100.00"))
                .phone("08011111111")
                .build();

        BuyDataPlanResponse buyDataPlanResponse = BuyDataPlanResponse.builder()
                .code("0000")
                .response_description("TRANSACTION SUCCESSFUL")
                .requestId("3476we129909djd")
                .amount("100.00")
                .purchasedCode("")
                .build();

        when(restTemplate.exchange(
                VTPassConstants.PAY_BILL,
                HttpMethod.POST,
                vtPassHttpEntity.getEntity(null),
                BuyDataPlanResponse.class
        )).thenReturn(new ResponseEntity<>(buyDataPlanResponse, HttpStatus.OK));

        BuyDataPlanResponse response = vtPassService.payDataPlan(request);
        assertEquals(buyDataPlanResponse, response);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllElectricityService() {
    }

    @Test
    void verifyElectricityMeter() {
    }

    @Test
    void buyElectricity() {
    }
}

    @Test
    void getAirtimeServices() {
        AirtimeServices airtimeServices = AirtimeServices.builder()
                .ServiceID("mtn")
                .amount(new BigDecimal(1000d))
                .name("MTN Airtime VTU")
                .image("https://sandbox.vtpass.com/resources/products/200X200/MTN-Airtime.jpg")
                .build();

      AirtimeServiceResponse airtimeServiceResponse = AirtimeServiceResponse.builder()
                .response_description("0000")
                .content(new ArrayList<>(List.of(airtimeServices)))
                .build();

      when(restTemplate.exchange(
              VTPassConstants.All_AIRTIME_SERVICES,
              HttpMethod.GET,
              vtPassHttpEntity.getEntity(null),
              AirtimeServiceResponse.class
      )).thenReturn(new ResponseEntity<>(airtimeServiceResponse,HttpStatus.OK));

      AirtimeServiceResponse airtimeServiceResponse1 = vtPassService.getAirtimeServices();
      assertEquals(airtimeServiceResponse,airtimeServiceResponse1);
    }

    @Test
    void buyAirtime(){
        BuyAirtimeRequest buyAirtimeRequest = BuyAirtimeRequest.builder()
                .request_id("202303110941Yan-he")
                .serviceID("airtime")
                .amount(new BigDecimal(1000d))
                .phone("08011111111")
                .build();

        BuyAirtimeResponse buyAirtimeResponse = BuyAirtimeResponse.builder()
                .requestId("20230311102003pby2p07")
                .response_description("TRANSACTION SUCCESSFUL")
                .transaction_date(new TransactionDate("20230211", 3,"Africa/Lagos"))
                .amount(new BigDecimal(1000d))
                .code(12)
                .transactionId("null")
                .purchase_code("")
                .build();

        when(restTemplate.exchange(
                VTPassConstants.PAY_BILL,
                HttpMethod.POST,
                vtPassHttpEntity.getEntity(null),
                BuyAirtimeResponse.class
        )).thenReturn(new ResponseEntity<>(buyAirtimeResponse, HttpStatus.OK));

        BuyAirtimeResponse airtimeResponse = vtPassService.buyAirtime(buyAirtimeRequest);
        assertEquals(buyAirtimeResponse, airtimeResponse);
    }
}
