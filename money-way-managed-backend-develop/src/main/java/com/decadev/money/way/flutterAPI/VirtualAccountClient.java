package com.decadev.money.way.flutterAPI;

import com.decadev.money.way.dto.request.VirtualAccountCreationRequest;
import com.decadev.money.way.dto.response.ErrorResponse;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VirtualAccountClient{
    private final RestTemplate restTemplate;
    private final RestTemplateUtils restTemplateUtils;



    public Object createVirtualAccount(VirtualAccountCreationRequest request) throws IOException {
        request.setAmount(100);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, restTemplateUtils.getHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(FlutterURLs.VIRTUAL_ACCOUNT_URL, HttpMethod.POST, httpEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                return objectMapper.readValue(responseBody, VirtualAccountCreationResponse.class);
            }
            return ErrorResponse.builder().timeStamp(LocalDateTime.now()).status((HttpStatus) response.getStatusCode()).message(response.getBody()).build();
        } catch (RestClientException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
