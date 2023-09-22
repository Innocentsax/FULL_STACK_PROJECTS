package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.request.FlwResolveAccountRequest;
import com.decagon.fintechpaymentapisqd11a.response.FlwGetAllBanksResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwResolveAccountDetails;
import com.decagon.fintechpaymentapisqd11a.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TransferServiceImplTest {

    @Test
    void getAllBanks() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwGetAllBanksResponse> httpEntity = new HttpEntity<>(null, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        FlwGetAllBanksResponse getAllBanksResponse = restTemplate.exchange(
                Constant.GET_ALL_BANKS,
                HttpMethod.GET,
                httpEntity,
                FlwGetAllBanksResponse.class
        ).getBody();

        assertEquals("success", getAllBanksResponse.getStatus());
        assertNotNull(getAllBanksResponse.getData());
    }

    @Test
    void resolveAccount() {
        FlwResolveAccountRequest resolveAccountRequest = new FlwResolveAccountRequest("0690000032", "044");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwResolveAccountRequest> accountRequestHttpEntity = new HttpEntity<>(resolveAccountRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        FlwResolveAccountDetails resolveAccountDetails = restTemplate.exchange(
                Constant.RESOLVE_ACCOUNT_DETAILS,
                HttpMethod.POST,
                accountRequestHttpEntity,
                FlwResolveAccountDetails.class
        ).getBody();

        assertEquals("success", resolveAccountDetails.getStatus());
        assertNotNull(resolveAccountDetails.getData());
    }
}