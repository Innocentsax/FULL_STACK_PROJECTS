package com.decadev.money.way.event;

import com.decadev.money.way.dto.response.NetworkDTO;
import com.decadev.money.way.repository.NetworkRepository;
import com.decadev.money.way.flutterAPI.FlutterURLs;
import com.decadev.money.way.flutterAPI.RestTemplateUtils;
import jakarta.annotation.PostConstruct;
import kong.unirest.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j

@RequiredArgsConstructor
public class ScheduleTaskService {
    private final NetworkRepository networkRepository;
    private final RestTemplate restTemplate;
    private final RestTemplateUtils restTemplateUtils;
    private final String baseUrl = FlutterURLs.BASE_URL;
    private final String networkCategoryUrl = FlutterURLs.NETWORK_CATEGORY;

    @PostConstruct
    public void onStartUp(){
        updateNetworks();
    }

    @Scheduled( cron = "${networks-reload-interval}") // update every 12 hours
    public void updateNetworks(){
        JsonObjectMapper objectMapper = new JsonObjectMapper();

        HttpEntity<String> request = new HttpEntity<>(restTemplateUtils.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(baseUrl+networkCategoryUrl,HttpMethod.GET,request,String.class );

        NetworkDTO networkDTO = objectMapper.readValue(response.getBody(),NetworkDTO.class);

        log.info ("networks: " +response.getBody().toString());
        log.info ("response: " +response.getStatusCode().value());

        if(response.getStatusCode().value() == 200 && networkDTO.getStatus().equals("success")){
            networkRepository.saveAll(networkDTO.getData());
        }
    }
}
