package com.goCash.services.implementations;

import com.goCash.dto.request.DataSubscriptionRequest;
import com.goCash.dto.response.DataSubscriptionResponse;
import com.goCash.dto.response.ElectricityBillResponse;
import com.goCash.entities.Transaction;
import com.goCash.entities.User;
import com.goCash.entities.WalletAccount;
import com.goCash.enums.TransactionStatus;
import com.goCash.enums.TransactionType;
import com.goCash.exception.UserNotFoundException;
import com.goCash.exception.WalletNotFoundException;
import com.goCash.repository.TransactionRepository;
import com.goCash.repository.UserRepository;
import com.goCash.repository.WalletRepository;
import com.goCash.services.DataService;
import com.goCash.utils.*;
import com.goCash.utils.purchase.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class DataServiceImpl implements DataService {

    @Value("${VT_PASS_BASE_URL}")
    private String baseUrl;

    @Value("${USERNAME}")
    private String username;

    @Value("${PASSWORD}")
    private String password;


    private final Util utilities;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    // private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ApiResponse getDataVariation(String provider) {
        String uri = "https://api-service.vtpass.com/api/service-variations?serviceID=" + provider;
        log.info("get details from VtPass api");

        Object[] objects = new Object[]{restTemplate.getForObject(
                uri,
                Object.class)};

        return ApiResponse.<List<Object>>builder()
                .code("00")
                .message("successful")
                .data(Arrays.stream(objects).toList())
                .httpStatus(HttpStatus.ACCEPTED)
                .build();


    }

    @Override
    @Transactional
    public ApiResponse purchaseDataBundle(DataSubscriptionRequest request) {

        request.setRequestId(RequestIdGenerator.RequestID());

        String authToken = getAuthToken(username, password);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("User-Agent", "Spring's Template");
        httpHeaders.add("Authorization", "Basic " + authToken);

        User appUser = userRepository.findByEmail(utilities.getLoginUser())
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));

        WalletAccount walletAccount = walletRepository.findByUser(appUser).
                orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        if (walletAccount.getBalance() < request.getAmount()) {
            log.error("User funds not sufficient");
            return ApiResponse.<ElectricityBillResponse>builder().message("Insufficient funds!")
                    .code("01").httpStatus(HttpStatus.BAD_REQUEST).build();
        } else {
            walletAccount.setBalance
                    (WalletBalanceUtility.subtractFunds(walletAccount.getBalance(), request.getAmount()));
            log.info("money deducted from user " + appUser.getId() + " account for data purchase transaction");
            walletRepository.save(walletAccount);

            Transaction transaction = new Transaction();
            transaction.setReference(request.getRequestId());
            transaction.setUser(appUser);
            transaction.setTransactionType(TransactionType.DATA_PURCHASE);
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setAmount(BigDecimal.valueOf(request.getAmount()));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction =  transactionRepository.save(transaction);

            log.info("Data purchase transaction saved to database");


            HttpEntity<DataSubscriptionRequest> httpRequest = new HttpEntity<>(request, httpHeaders);

            ResponseEntity<PurchaseResponse> responseEntity = restTemplate.postForEntity(
                    (baseUrl), httpRequest, PurchaseResponse.class);

            PurchaseResponse purchaseResponse = responseEntity.getBody();
            HttpStatus statusCode = responseEntity.getStatusCode();


            if (statusCode.is2xxSuccessful() && (purchaseResponse.getContent().getTransactions() != null)) {


                if (purchaseResponse.getCode().equals("000")) {
                    transaction.setMessage(purchaseResponse.getCode());
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);
                    transaction.setProviderReference(purchaseResponse.getContent().getTransactions().getProduct_name());
                    transaction.setProviderMessage(purchaseResponse.getResponse_description());
                   transactionRepository.save(transaction);
                    log.info("Data purchase transaction re-saved to database");
                } else {
                    transaction.setMessage(purchaseResponse.getCode());
                    transaction.setStatus(TransactionStatus.FAILED);
                    transaction.setProviderReference(purchaseResponse.getContent().getTransactions().getProduct_name());
                    transaction.setProviderMessage(purchaseResponse.getResponse_description());
                    transactionRepository.save(transaction);
                    log.info("Data purchase transaction re-saved to database");

                    walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds
                            (walletAccount.getBalance(), request.getAmount()));
                    walletRepository.save(walletAccount);
                }
                return ApiResponse.<DataSubscriptionResponse>builder()
                        .code(purchaseResponse.getCode())
                        .message(purchaseResponse.getResponse_description())
                        .data(EntityMapper.newPurchaseResponse(purchaseResponse))
                        .httpStatus(HttpStatus.OK)
                        .build();
            } else {

                walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds
                        (walletAccount.getBalance(), request.getAmount()));
                walletRepository.save(walletAccount);

                log.error("API call failed with status code: {}", statusCode);
                return ApiResponse.<DataSubscriptionResponse>builder()
                        .code(purchaseResponse.getCode())
                        .message(purchaseResponse.getResponse_description())
                        .httpStatus(HttpStatus.OK)
                        .build();
            }
        }
    }
    private String getAuthToken (String username, String password){
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }

}
