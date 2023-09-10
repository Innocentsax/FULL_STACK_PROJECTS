package com.goCash.services.implementations;

import com.goCash.dto.request.ElectricityBillQueryRequest;
import com.goCash.dto.request.ElectricityBillRequest;
import com.goCash.dto.response.ElectricityBillQueryResponse;
import com.goCash.dto.response.ElectricityBillResponse;
import com.goCash.entities.Transaction;
import com.goCash.entities.User;
import com.goCash.entities.WalletAccount;
import com.goCash.enums.TransactionStatus;
import com.goCash.enums.TransactionType;
import com.goCash.exception.ApiCallException;
import com.goCash.exception.UserNotFoundException;
import com.goCash.exception.WalletNotFoundException;
import com.goCash.repository.TransactionRepository;
import com.goCash.repository.UserRepository;
import com.goCash.repository.WalletRepository;
import com.goCash.services.ElectricityBillService;
import com.goCash.utils.*;
import com.goCash.utils.purchase.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;


@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityBillServiceImpl implements ElectricityBillService {

    private final RestTemplate restTemplate;
    private final Util utilities;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final EntityMapper entityMapper;

    @Value("${USERNAME}")
    private String authUsername;

    @Value("${PASSWORD}")
    private String authPassword;

    @Value("${ELECTRICITY_BILL_URL}")
    private String electricityBillUrl;

    @Value("${electricityBill_url}")
    private String electricityBillQueryUrl;

    private String getAuthToken(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }

    @Override
    public ApiResponse<ElectricityBillResponse> payElectricityBill(ElectricityBillRequest electricityBillRequest) {


        User appUser = userRepository.findByEmail(utilities.getLoginUser())
                .orElseThrow(()-> new UserNotFoundException("user does not exist"));
        WalletAccount walletAccount = walletRepository.findByUser(appUser).
                orElseThrow(()->new WalletNotFoundException("wallet not found"));

        electricityBillRequest.setRequestId(RequestIdGenerator.RequestID());

        if (walletAccount.getBalance() < electricityBillRequest.getAmount()) {
            log.error("User funds not sufficient");
            return ApiResponse.<ElectricityBillResponse>builder().message("Insufficient funds!")
                    .code("01").httpStatus(HttpStatus.BAD_REQUEST).build();
        } else {
            walletAccount.setBalance
                    (WalletBalanceUtility.subtractFunds(walletAccount.getBalance(), electricityBillRequest.getAmount()));
            log.info("money deducted from user " + appUser.getId() + " account for electricity bill transaction");
            walletRepository.save(walletAccount);

            Transaction transaction = new Transaction();
            transaction.setReference(electricityBillRequest.getRequestId());
            transaction.setUser(appUser);
            transaction.setTransactionType(TransactionType.BillPAYMENT);
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setAmount(BigDecimal.valueOf(electricityBillRequest.getAmount()));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setMessage("Transaction Pending");
            transaction.setProviderMessage("Awaiting provider message");
            transactionRepository.save(transaction);
            log.info("Electricity bill payment transaction of user " + appUser.getId() + " is saved to database " + electricityBillRequest.getRequestId());


            String authToken = getAuthToken(authUsername, authPassword);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            httpHeaders.add("User-Agent", "Spring's Template");
            httpHeaders.add("Authorization", "Basic " + authToken);

            HttpEntity<ElectricityBillRequest> electricityBillRequestHttpEntity = new HttpEntity<>
                    (electricityBillRequest, httpHeaders);
            ResponseEntity<PurchaseResponse> responseEntity = restTemplate.postForEntity(
                    (electricityBillUrl),
                    electricityBillRequestHttpEntity,
                    PurchaseResponse.class
            );

            PurchaseResponse response = responseEntity.getBody();
            HttpStatus statusCode = responseEntity.getStatusCode();

            if (statusCode.is2xxSuccessful()) {

                log.info("ApiResponse: {}", responseEntity.getBody());

                transaction.setStatus(TransactionStatus.SUCCESSFUL);
                transaction.setMessage("Transaction successful");
                assert response != null;
                transaction.setProviderMessage(response.getResponse_description());
                transaction.setUpdatedAt(LocalDateTime.now());
                transactionRepository.save(transaction);
                log.info("Electricity bill payment transaction of user " + appUser.getId() + " is successful and DB is updated " + electricityBillRequest.getRequestId());

            } else {

                transaction.setStatus(TransactionStatus.FAILED);
                transaction.setMessage("Transaction failed");
                assert response != null;
                transaction.setProviderMessage(response.getResponse_description());
                transaction.setUpdatedAt(LocalDateTime.now());
                transactionRepository.save(transaction);

                walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds
                        (walletAccount.getBalance(), electricityBillRequest.getAmount()));
                walletRepository.save(walletAccount);
                log.info("Electricity bill payment transaction of user " + appUser.getId() + " failed and fund is reversed " + electricityBillRequest.getRequestId());

                log.error("API call failed with status code: {}", statusCode);
                throw new ApiCallException("Transaction failed", statusCode);
            }
            return ApiResponse.<ElectricityBillResponse>builder()
                    .code(response.getPurchased_code())
                    .message(response.getResponse_description())
                    .data(entityMapper.newBillResponse(response))
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }


    @Override
    public ApiResponse verifyMeterNumber(ElectricityBillQueryRequest requestBody) {

        String authToken = getAuthToken(authUsername, authPassword);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("User-Agent", "Spring's Template");
        httpHeaders.add("Authorization", "Basic " + authToken);

        HttpEntity<ElectricityBillQueryRequest> httpRequest = new HttpEntity<>
                (requestBody, httpHeaders);
        ResponseEntity<ElectricityBillQueryResponse> responseEntity = restTemplate.postForEntity(
                (electricityBillQueryUrl),
                httpRequest,
                ElectricityBillQueryResponse.class
        );

        HttpStatus statusCode = responseEntity.getStatusCode();


        if (statusCode.is2xxSuccessful()) {
            log.info("ApiResponse: {}", responseEntity.getBody());
            return ApiResponse.<ElectricityBillQueryResponse>builder()
                    .code("00")
                    .message("Transaction Successful")
                    .data(responseEntity.getBody())
                    .httpStatus(HttpStatus.OK)
                    .build();

        } else {
            log.error("API call failed with status code: {}", statusCode);
            throw new ApiCallException("API call failed", statusCode);
        }
    }

}
