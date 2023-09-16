package com.decagon.chompapp.services.paystack;

import com.decagon.chompapp.dtos.paystack.InitializeTransactionRequestDto;
import com.decagon.chompapp.dtos.paystack.InitializeTransactionResponseDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Service
public class PaymentServiceImpl implements PaymentService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${paystack.secret.key}")
    private String key;


    @Override
    public InitializeTransactionResponseDto initializeTransaction
            (InitializeTransactionRequestDto transactionRequestDto) {

        String url = "https://api.paystack.co/transaction/initialize";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + key);

        HttpEntity<InitializeTransactionRequestDto> entity =
                new HttpEntity<>(transactionRequestDto, headers);

        ResponseEntity<InitializeTransactionResponseDto> response =
                restTemplate.postForEntity(url, entity, InitializeTransactionResponseDto.class);

        return response.getBody();
    }

    @Override
    public StringBuilder verifyTransaction (String reference) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
        request.addHeader("Content-type", "application/json");
        request.addHeader(AUTHORIZATION, "Bearer " + key);

        StringBuilder result = new StringBuilder();

        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } else {
            throw new IOException("Error Occurred while connecting to pay-stack url");
        }
        return result;
    }
}
