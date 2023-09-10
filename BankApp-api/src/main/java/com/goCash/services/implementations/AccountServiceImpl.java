package com.goCash.services.implementations;

import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.response.AccountVerificationResponse;
import com.goCash.dto.response.FlutterWaveApiResponse;
import com.goCash.entities.AccountData;
import com.goCash.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

    private final RestTemplate restTemplate;

    @Value("${Flutterwave_key}")
    private String authToken;

    @Value("${BASE_URL}")
    private String flutterwaveApiUrl;

    @Autowired
    public AccountServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FlutterWaveApiResponse bankVerification(AccountVerificationRequest apiRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authToken);

        HttpEntity<AccountVerificationRequest> apiRequestHttpEntity = new HttpEntity<>(apiRequest, headers);

        try {
            ResponseEntity<AccountVerificationResponse> responseEntity = restTemplate.postForEntity(
                    flutterwaveApiUrl,
                    apiRequestHttpEntity, AccountVerificationResponse.class);

            AccountVerificationResponse response = responseEntity.getBody();

            if ("success".equals(response.getStatus())) {
                AccountData accountData = response.getData();
                if (accountData != null && accountData.getAccountName() != null) {
                    FlutterWaveApiResponse customResponse = new FlutterWaveApiResponse("success", "Account details fetched");
                    customResponse.setAccountNumber(accountData.getAccountNumber());
                    customResponse.setAccountName(accountData.getAccountName());
                    return customResponse;
                } else {
                    return new FlutterWaveApiResponse("error", "Account owner not available");
                }
            } else {
                String errorMessage = response.getMessage();
                if (errorMessage.contains("invalid account")) {
                    return new FlutterWaveApiResponse("error", "Sorry, that account number is invalid, please check and try again");
                } else {
                    return new FlutterWaveApiResponse("error", errorMessage);
                }
            }
        } catch (HttpClientErrorException e) {
            return new FlutterWaveApiResponse("error", e.getMessage());
        } catch (RestClientException e) {
            return new FlutterWaveApiResponse("error", "Failed to communicate with the API");
        }
    }
}

