package com.decagon.service.serviceImplementation;

import com.decagon.domain.entity.Banks;
import com.decagon.dto.request.BankData;
import com.decagon.exception.BankNotFoundException;
import com.decagon.repository.BankRespository;
import com.decagon.service.AccountService;
import com.decagon.utils.HeadersUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements ApplicationRunner, AccountService {
    private final BankRespository bankRespository;
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;
private final ProfileServiceImpl profileService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        HeadersUtils.apiDetails(headers);
//        RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create("https://api.paystack.co/bank"));
//        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity,String.class);
//        List<BankData> bankDataList = new ObjectMapper().convertValue(new JSONParser(Objects.requireNonNull(responseEntity.getBody())).object().get("data"), new TypeReference<List<BankData>>() {});
//        bankRespository.deleteAll();
//        if(bankRespository.findAll().isEmpty()){
//            bankDataList.forEach(add ->{
//                bankRespository.save(new Banks(add.getCode(),add.getName()));
//            });
//        }
    }
    @Override
    public Object getBankCodeAndSend(String bankName, String accountNumber,String authorizationHeader) {
          String userid = profileService.getUserID(authorizationHeader);

        HeadersUtils.apiDetails(headers);
        Banks findName = bankRespository.findBanksByBankName(bankName).orElseThrow(()-> new BankNotFoundException(bankName));
        String url="https://api.paystack.co/bank/resolve";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("account_number",accountNumber)
                .queryParam("bank_code",findName.getBankCode())
                .queryParam("currency","NGN");
        String urlbulder = uriComponentsBuilder.toUriString();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(urlbulder, HttpMethod.GET, requestEntity, Object.class);
        Object responseBody = responseEntity.getBody();
        return responseBody;


    }
}
