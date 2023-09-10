package com.goCash.services.implementations;

import com.goCash.dto.request.AirtimePurchaseRequest;
import com.goCash.dto.response.AirtimePurchaseResponse;
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
import com.goCash.services.AirtimePurchaseServices;
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
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirtimePurchaseServiceImplementation implements AirtimePurchaseServices {

    private final Util util;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final VtPassAuthTokenGenerator vtPassAuthTokenGenerator;
    private final ResponseManager responseManager;
    private final EntityMapper entityMapper;



    @Value("${USERNAME}")
    private String getAuthUsername;

    @Value("${PASSWORD}")
    private String getAuthPassword;

    @Value("${AIRTIME_RECHARGE_URL}")
    private String airtimeRecharge_url;


    @Override
    @Transactional
    public ApiResponse purchaseAirtime(AirtimePurchaseRequest request) {

        request.setRequest_id(RequestIdGenerator.RequestID());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "Spring's Template");
        headers.add("Authorization", "Basic "
                + vtPassAuthTokenGenerator.getAuthToken(getAuthUsername, getAuthPassword));

        User appUser = userRepository.findByEmail(util.getLoginUser())
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
        WalletAccount walletAccount = walletRepository.findByUser(appUser).
                orElseThrow(() -> new WalletNotFoundException("wallet not found"));


        Transaction transaction = new Transaction();
        transaction.setReference(request.getRequest_id());
        transaction.setUser(appUser);
        transaction.setTransactionType(TransactionType.AIRTIME_PURCHASE);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAmount(BigDecimal.valueOf(request.getAmount()));
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        log.info("Airtime purchase transaction saved to database");

        ApiResponse validateFunds = responseManager.airtimePurchaseValidation(walletAccount.getBalance(), request.getAmount(), transaction);

        if(!validateFunds.getCode().equals("000")){
           return ApiResponse.<ApiResponse>builder()
                    .message(validateFunds.getMessage())
                    .code(validateFunds.getCode())
                    .httpStatus(validateFunds.getHttpStatus())
                    .build();
        }

        walletAccount.setBalance
                (WalletBalanceUtility.subtractFunds(walletAccount.getBalance(), request.getAmount()));
        log.info("money deducted from user account for airTime transaction");
        walletRepository.save(walletAccount);

        HttpEntity<AirtimePurchaseRequest> httpRequest = new HttpEntity<>(request, headers);
        ResponseEntity<PurchaseResponse> responseEntity = restTemplate.postForEntity(
                (airtimeRecharge_url), httpRequest, PurchaseResponse.class);

        PurchaseResponse purchaseResponse = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode.is2xxSuccessful() && (purchaseResponse.getContent().getTransactions() != null)) {

            if (purchaseResponse.getCode().equals("000")) {
                transaction.setMessage(purchaseResponse.getCode());
                transaction.setStatus(TransactionStatus.SUCCESSFUL);
                transaction.setProviderReference(purchaseResponse.getContent().getTransactions().getProduct_name());
                transaction.setProviderMessage(purchaseResponse.getResponse_description());
                transactionRepository.save(transaction);
                log.info("Airtime purchase transaction re-saved to database");
            } else {
              transaction.setMessage(purchaseResponse.getCode());
              transaction.setStatus(TransactionStatus.FAILED);
              transaction.setProviderReference(purchaseResponse.getContent().getTransactions().getProduct_name());
              transaction.setProviderMessage(purchaseResponse.getResponse_description());
              transactionRepository.save(transaction);
              log.info("Airtime purchase transaction re-saved to database");

              walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds
                      (walletAccount.getBalance(), request.getAmount()));
              walletRepository.save(walletAccount);
            }
            return ApiResponse.<AirtimePurchaseResponse>builder()
                    .code(purchaseResponse.getCode())
                    .message(purchaseResponse.getResponse_description())
                    .data(entityMapper.airtimePurchaseResponse(purchaseResponse))
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else
        {
            walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds
                    (walletAccount.getBalance(), request.getAmount()));
            walletRepository.save(walletAccount);

            log.error("API call failed with status code: {}", statusCode);
            return ApiResponse.<AirtimePurchaseResponse>builder()
                    .code(purchaseResponse.getCode())
                    .message(purchaseResponse.getResponse_description())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
}

