package com.decadev.money.way.flutterAPI;

import com.decadev.money.way.dto.request.AirtimeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AirtimeClient {
    private final RestTemplateUtils restTemplateUtils;
    private final RestTemplate restTemplate;
//981eb33ed668fb84e07b86d21f89553f
    private String publicKey=  "PK_917f5f963aa779e4aba51f607d66069a189819a225b";
    private String secretKey=  "SK_7977e680e5a28628c7d09bcc520fd6a033dcf2742e1";

    public Object payAirtimeBill(AirtimeRequest request1) throws JsonProcessingException {
        AirtimeRequest request = AirtimeRequest.builder()
                .request_id(request1.request_id)
                .phone(new BigDecimal(request1.getPhone_x()))
                .amount(request1.getAmount())
                .serviceID(request1.getServiceID())
                .build();

        ObjectMapper object = new ObjectMapper();
        String jsonString = object.writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

// Set the VTPass public key and secret key in the request headers for authentication
        headers.set("public-key", publicKey);
        headers.set("secret-key", secretKey);

        HttpEntity<String> httpRequest = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://sandbox.vtpass.com/api/pay", HttpMethod.POST, httpRequest, String.class);



        return  response;
    }

}
