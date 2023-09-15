package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.Amount;
import com.example.cedarxpressliveprojectjava010.dto.InitializeTransactionRequestDto;
import com.example.cedarxpressliveprojectjava010.dto.InitializeTransactionResponseDto;
import com.example.cedarxpressliveprojectjava010.service.InitializeTransactionService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class InitializeTransactionServiceImpl implements InitializeTransactionService {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${pay.stack.url}")
    private String url;
    @Value("${pay.stack.secret.key}")
    private String key;

    @Override
    public InitializeTransactionResponseDto initializeTransactionResponseDto(Amount amount) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String value = amount.getAmount()+"00";
        InitializeTransactionRequestDto initializeTransactionRequestDto = new InitializeTransactionRequestDto();
        initializeTransactionRequestDto.setAmount(value);
        initializeTransactionRequestDto.setEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer " + key);
        HttpEntity<InitializeTransactionRequestDto> entity = new HttpEntity<>(initializeTransactionRequestDto,headers);
        ResponseEntity<InitializeTransactionResponseDto> response = restTemplate.postForEntity(url,entity,InitializeTransactionResponseDto.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<String> verifyPayment(String reference) throws IOException {
        String status = null;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
        request.addHeader("Content-type", "application/json");
        request.addHeader("Authorization", "Bearer " + key);

        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            int count  = 0;
            String line;
            while ((line = rd.readLine()) != null) {
                line = line.trim();
                if(line.startsWith("\"status") && count > 2){
                    status = checkLine(line);
                }
                count++;
            }
        } else {
            throw new IOException("Error Occurred while connecting to pay-stack url");
        }

        return ResponseEntity.ok(status);
    }

    private String checkLine(String line){
        if(line.contains("success")){
            return "success";
        }else if(line.contains("abandoned"))return "abandoned";
        else return "failed";
    }

}