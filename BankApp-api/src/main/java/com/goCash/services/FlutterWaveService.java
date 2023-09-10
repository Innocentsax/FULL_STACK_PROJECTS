package com.goCash.services;

import com.goCash.dto.request.*;
import com.goCash.dto.response.TransferResponse;
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
import com.goCash.utils.ApiResponse;
import com.goCash.utils.ReferenceGenerator;
import com.goCash.utils.Util;
import com.goCash.utils.WalletBalanceUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlutterWaveService {
    private final RestTemplate restTemplate;
    private final Util utilities;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;


    @Value("${flutterwave.authkey}")
    private String fluttertoken;
    @Value("${flutterWaveTransferKey}")
    private String transferToken;
    @Value("${transferToBankUrl}")
    private String TransferToBankUrl;

    public ResponseEntity<String> createVirtualAcccount(UserRegistrationRequest request) {
        log.info("method that calls the flutterwave api");
        CreateWalletRequest walletRequestrequest = CreateWalletRequest.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .bvn(request.getBvn())
                .phonenumber(request.getPhoneNumber())
                .is_permanent(true)
                .tx_ref("GOC"+ LocalDateTime.now())
                .amount(1)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", fluttertoken);
        log.info("Calling the api now");

        ResponseEntity<String> exchange = restTemplate.exchange(
                "https://api.flutterwave.com/v3/virtual-account-numbers",
                HttpMethod.POST,
                new HttpEntity<>(walletRequestrequest, headers),
                String.class


        );
        log.info(String.valueOf(exchange));
        return exchange;
    }

    public ApiResponse<TransferResponse> transferToBank(TransferRequestDto transferRequestDto) {
        log.info("Method to transfer money from the wallet to bank");
        User appUser = userRepository.findByEmail(utilities.getLoginUser())
                .orElseThrow(()-> new UserNotFoundException("user does not exist"));
        WalletAccount walletAccount =  walletRepository.findByUser(appUser).
                orElseThrow(()->new WalletNotFoundException("wallet not found"));
        Meta meta = new Meta();
        meta.setEmail(appUser.getEmail());
        meta.setFirstName(appUser.getFirstName());
        meta.setLastName(appUser.getLastName());
        meta.setPhoneNumber(appUser.getPhoneNumber());
        meta.setAddress(appUser.getAddress());

        TransferRequest transferRequest = new TransferRequest(transferRequestDto,meta);
        transferRequest.setReference(ReferenceGenerator.generateRandomString(7));

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(transferRequest.getAmount()));
        transaction.setTransactionType(TransactionType.BANK_TRANSFER);
        transaction.setReference(transferRequest.getReference());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setUser(appUser);
        transaction = transactionRepository.save(transaction);

        if (walletAccount.getBalance() < transferRequest.getAmount()) {
            log.error("The balance is insufficient");
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            return ApiResponse.<TransferResponse>builder()
                    .message("Amount not enough")
                    .code("01").
                    httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }else {
            log.info("deduct the amount from the wallet balance");
            walletAccount.setBalance(walletAccount.getBalance() - transferRequest.getAmount());
            walletAccount = walletRepository.save(walletAccount);

        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer" + transferToken);
        headers.setBearerAuth(transferToken);


        HttpEntity<TransferRequest> transferRequestHttpEntity = new HttpEntity<>
                (transferRequest, headers);
        ResponseEntity<TransferResponse> responseEntity = restTemplate.postForEntity(TransferToBankUrl, transferRequestHttpEntity,
                TransferResponse.class);

        TransferResponse result = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode.is2xxSuccessful()) {

            if(result.getStatus().equals("success")) {
                transaction.setProviderMessage(result.getMessage());
                transaction.setMessage("FlutterWave/goCash");
                transaction.setStatus(TransactionStatus.SUCCESSFUL);
                transactionRepository.save(transaction);
                log.info("transaction saved when successful");
            }else{
                transaction.setProviderMessage(result.getMessage());
                transaction.setMessage("Flutterwave/goCash");
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);
                log.info("transaction saved when failed");
                walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds(walletAccount.getBalance(),transferRequest.getAmount()));
                walletRepository.save(walletAccount);

            }

            return ApiResponse.<TransferResponse>builder()
                    .data(result)
                    .message(result.getStatus())
                    .code("00")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {

            walletAccount.setBalance(WalletBalanceUtility.reverseDeductedFunds(walletAccount.getBalance(),transferRequest.getAmount()));
            walletRepository.save(walletAccount);

        }

        return ApiResponse.<TransferResponse>builder()
                .data(result)
                .message("successful")
                .code("00")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
