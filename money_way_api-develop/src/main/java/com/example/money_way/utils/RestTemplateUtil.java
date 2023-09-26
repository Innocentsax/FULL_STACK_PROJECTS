package com.example.money_way.utils;

import com.example.money_way.dto.request.*;
import com.example.money_way.dto.response.*;
import com.example.money_way.dto.response.BanksResponse;
import com.example.money_way.dto.response.DataPurchaseResponse;
import com.example.money_way.dto.response.TransferFeeResponse;
import com.example.money_way.dto.response.TransferToBankResponse;
import com.example.money_way.model.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RestTemplateUtil {
    private final RestTemplate restTemplate;
    private final EnvironmentVariables environmentVariables;

    public HttpHeaders  getVTPASS_Header() {
        HttpHeaders header = new HttpHeaders();
        header.add("api-key", environmentVariables.getVTPASS_API_KEY());
        header.add("secret-key", environmentVariables.getVTPASS_Secret_Key());
        header.add("public-key", environmentVariables.getVTPASS_Public_Key());
        header.setContentType((MediaType.APPLICATION_JSON));
        return header;
    }

    public HttpHeaders headersForFlutterwave(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + environmentVariables.getFLW_SECRET_KEY());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public BanksResponse fetchAllBanksFromFlutterwave(){
        HttpHeaders headers = headersForFlutterwave();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(environmentVariables.getGetBankUrl(),
                HttpMethod.GET, entity, BanksResponse.class).getBody();
    }

    public TransferFeeResponse getTransferFeeFromFlutterwave(BigDecimal amount){
        HttpHeaders headers = headersForFlutterwave();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder url = UriComponentsBuilder
                .fromUriString(environmentVariables.getGetTransferFeeUrl())
                .queryParam("amount", amount)
                .queryParam("currency", "NGN");

        return restTemplate.exchange(url.toUriString(),
                HttpMethod.GET, entity, TransferFeeResponse.class).getBody();
    }

    public TransferToBankResponse transferToBankWithFlutterwave(
            TransferToBankDto transferToBankDto, String ref){
        HttpHeaders headers = headersForFlutterwave();

        TransferToBankRequest requestBody = TransferToBankRequest.builder()
                .account_bank(transferToBankDto.getBankCode())
                .account_number(transferToBankDto.getAccount_number())
                .amount(transferToBankDto.getAmount())
                .narration(transferToBankDto.getDescription())
                .currency("NGN")
                .reference(ref)
                .build();

        HttpEntity<TransferToBankRequest> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(environmentVariables.getGetTransferToBankUrl(),
                HttpMethod.POST, entity, TransferToBankResponse.class).getBody();
    }

    public TransferToBankResponse retryTransferToBankWithFlutterwave(
            TransferToBankResponse transferToBankResponse){

        HttpHeaders headers = headersForFlutterwave();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", (String) transferToBankResponse.getData().get("id"));

        return restTemplate.exchange(environmentVariables.getGetRetryTransferToBankUrl(),
                HttpMethod.POST, entity, TransferToBankResponse.class, uriVariables).getBody();
    }

    public DataVariationsResponse fetchDataVariations(String dataServiceProvider){
        HttpHeaders header = getVTPASS_Header();
        HttpEntity<?> entity = new HttpEntity<>(header);
        return restTemplate.exchange(environmentVariables.getFetchDataVariations()+"serviceID="+dataServiceProvider+"-data",
                HttpMethod.GET, entity, DataVariationsResponse.class).getBody();

    }
    public BillResponse getVTPassElectricityBillResponse(ElectricityRequestDto requestDto) {
        HttpHeaders headers = getVTPASS_Header();
        HttpEntity<ElectricityRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.exchange(environmentVariables.getElectricityBillsUrl(),
                HttpMethod.POST, entity, BillResponse.class).getBody();

    }
    public DataPurchaseResponse getDataPurchaseResponse(DataRequestDto dataRequestDto) {
        HttpHeaders headers = getVTPASS_Header();
        HttpEntity<DataRequestDto> entity = new HttpEntity<>(dataRequestDto, headers);

        return restTemplate.exchange(environmentVariables.getPurchaseDataUrl(),
                HttpMethod.POST, entity, DataPurchaseResponse.class).getBody();
    }
    public TvPurchaseResponse getTvPurchaseResponse(VtPassTvPurchaseRequest vtPassRequest) {
        HttpHeaders headers = getVTPASS_Header();

        HttpEntity<VtPassTvPurchaseRequest> entity = new HttpEntity<>(vtPassRequest, headers);
        return restTemplate.exchange(environmentVariables.getPurchaseSubscriptionUrl(),
                HttpMethod.POST, entity, TvPurchaseResponse.class).getBody();
    }

    public TvVariationsResponse fetchTvVariations(String tvServiceProvider){
        HttpHeaders headers = getVTPASS_Header();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(environmentVariables.getFetchTvVariations() + "serviceID=" + tvServiceProvider,
                HttpMethod.GET, entity, TvVariationsResponse.class).getBody();
    }

}
