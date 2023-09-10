package com.goCash.services.implementations;

import com.goCash.dto.request.BankNamesRequest;
import com.goCash.dto.response.BankNamesResponse;
import com.goCash.services.BankNamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankNamesServiceImpl implements BankNamesService {

    private final RestTemplate restTemplate;
        @Value("${api_url}")
                private String apiUrl;
        @Value("${flutterwave.authkey}")
    private  String authToken;

    @Override
    public List<BankNamesRequest> getAllBanks() {
        HttpHeaders headers = createRequestHeaders();

        ResponseEntity<BankNamesResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                BankNamesResponse.class
        );

        return response.getBody().getData();
    }

    private HttpHeaders createRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "Spring's RestTemplate");
        headers.add("Authorization", "Bearer " + authToken);

        return headers;
    }
}
