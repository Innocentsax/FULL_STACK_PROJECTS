package com.decadev.money.way.flutterAPI;

import com.decadev.money.way.dto.request.BankTransferRequest;
import com.decadev.money.way.dto.request.CashTransferRequest;
import com.decadev.money.way.dto.request.FundWalletRequest;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.enums.Status;
import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.enums.TransactionType;
import com.decadev.money.way.event.EventListener;
import com.decadev.money.way.exception.TransactionException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.Transaction;
import com.decadev.money.way.model.User;
import com.decadev.money.way.repository.TransactionRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.service.TransactionService;
import com.decadev.money.way.utils.SecurityUtils;
import com.decadev.money.way.utils.TransactionReferenceGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
public class FLWFundWallet implements FlutterService{
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;
    private final EventListener eventListener;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final RestTemplateUtils restTemplateUtils;
    private final TransactionService transactionService;

    @Override
    public VirtualAccountCreationResponse bankAccountCharge(FundWalletRequest request) throws JsonProcessingException, UserNotFoundException {
        User fundSender = userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername()).orElseThrow(()->new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getPin(), fundSender.getPin())) throw new TransactionException("Incorrect pin");

        FundWalletRequest newRequest = FundWalletRequest.builder()
                .account_bank(request.getAccount_bank())
                .account_number(request.getAccount_number())
                .fullname(request.getFullname())
                .email(fundSender.getEmail())
                .tx_ref(TransactionReferenceGenerator.generateReference())
                .currency("NGN")
                .amount(request.getAmount())
                .build();

        ObjectMapper objectMapper= new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(newRequest);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, restTemplateUtils.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(FlutterURLs.CHARGE_ACCOUNT_URL, HttpMethod.POST, httpEntity, String.class);

        //return raw response

        if(response.getStatusCode() == HttpStatus.OK){
            String responseBody = response.getBody();

            VirtualAccountCreationResponse successResponse = objectMapper.readValue(responseBody, VirtualAccountCreationResponse.class);

            Transaction transaction = Transaction.builder()
                    .transactionType(TransactionType.CASH_TRANSFER)
                    .category(TransactionCategory.CREDIT)
                    .cashTransferType("Wallet Funding")
                    .date(LocalDateTime.now())
                    .user(fundSender)
                    .referenceId(newRequest.getTx_ref())
                    .status(Status.SUCCESS)
                    .description(request.getDescription())
                    .amount(request.getAmount())
                    .build();

            eventListener.sendTransactionDetails(transaction);

            fundSender.getTransactions().add(transactionRepository.save(transaction));
            fundSender.getWallet().setAccountBalance(transactionService.creditRecipientWallet(request.getAmount(), fundSender.getWallet()));

            userRepository.save(fundSender);

            return successResponse;
        }
        throw new TransactionException(response.getBody());
    }

    @Override
    public Object transferToOtherBank(CashTransferRequest request) throws JsonProcessingException {
        //Data transfer
        BankTransferRequest bankTransferRequest = BankTransferRequest.builder()
                .account_bank(request.getAccountBank())
                .reference(TransactionReferenceGenerator.generateReference())
                .narration(request.getDescription())
                .debit_currency("NGN")
                .currency("NGN")
                .callback_url("https://webhook.site/9d6f2b76-80ce-4777-9fac-0ab12c7398d6")
                .account_number(request.getAccountNumber())
                .amount(Integer.parseInt(request.getAmount()))
                .build();

        ObjectMapper objectMapper =  new ObjectMapper();
        String jsonString =  objectMapper.writeValueAsString(bankTransferRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, restTemplateUtils.getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(FlutterURLs.BANK_TRANSFER_URL, HttpMethod.POST, requestEntity, String.class);


        return response;
    }

}
