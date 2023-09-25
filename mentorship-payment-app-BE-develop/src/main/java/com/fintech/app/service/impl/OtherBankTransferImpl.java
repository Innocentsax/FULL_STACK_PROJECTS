package com.fintech.app.service.impl;

import com.fintech.app.model.Transfer;
import com.fintech.app.model.User;
import com.fintech.app.model.Wallet;
import com.fintech.app.repository.TransferRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.request.OtherBankTransferRequest;
import com.fintech.app.request.TransferRequest;
import com.fintech.app.request.VerifyTransferRequest;
import com.fintech.app.response.*;
import com.fintech.app.model.FlwBank;
import com.fintech.app.request.FlwAccountRequest;
import com.fintech.app.service.OtherBankTransferService;
import com.fintech.app.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OtherBankTransferImpl implements OtherBankTransferService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${FLW_SECRET_KEY}")
    private String AUTHORIZATION;

    @Value("${callback_url}")
    private String callbackUrl;

    @Autowired
    public OtherBankTransferImpl(UserRepository userRepository,
                                 WalletRepository walletRepository,
                                 TransferRepository transferRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<FlwBank> getBanks() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ AUTHORIZATION);

        HttpEntity<FlwBankResponse> request = new  HttpEntity<>(null, headers);

        FlwBankResponse flwBankResponse = restTemplate.exchange(
                Constant.GET_BANKS_API +"/NG",
                HttpMethod.GET,
                request,
                FlwBankResponse.class).getBody();

        assert flwBankResponse != null;
        return flwBankResponse.getData();
    }

    @Override
    public BaseResponse<FlwAccountResponse> resolveAccount(FlwAccountRequest flwAccountRequest) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ AUTHORIZATION);

        HttpEntity<FlwAccountRequest> request = new  HttpEntity<>(flwAccountRequest, headers);

        FlwAccountResponse response = restTemplate.exchange(
                Constant.RESOLVE_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwAccountResponse.class).getBody();

        return new BaseResponse<>(HttpStatus.OK, "Account Resolved", response);
    }


    @Transactional
    @Override
    public BaseResponse<VerifyTransferResponse> verifyTransaction(VerifyTransferRequest verifyTransferRequest) {
        log.info(verifyTransferRequest.toString());

        Long flwRef = verifyTransferRequest.getData().getId();
        Transfer transfer = transferRepository.findByFlwRef(flwRef)
                .orElse(null);

        if(transfer != null){
            String status = verifyTransferRequest.getData().getStatus();
            transfer.setStatus(status);
            transferRepository.save(transfer);
        }
        return new BaseResponse<>(HttpStatus.OK,"verify successfully",null);
    }


    @Override
    public BaseResponse<OtherBankTransferResponse> initiateOtherBankTransfer(TransferRequest transferRequest) {
//        User user = retrieveUserDetails();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED, "Not logged in", null);
        }
        if(!validatePin(transferRequest.getPin(), user))
           return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Incorrect pin", null);
        if(!validateRequestBalance(transferRequest.getAmount()))
           return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid amount", null);
        if(!validateWalletBalance(transferRequest.getAmount(), user))
           return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Insufficient balance", null);

        String clientRef = UUID.randomUUID().toString();
           //call API
        OtherBankTransferResponse response = otherBankTransfer(transferRequest, clientRef);

        if (!response.getStatus().equalsIgnoreCase("success")) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "An error occured", null);
        }
        // save transfer
        Transfer transfer = saveTransactions(user, transferRequest);
        transfer.setClientRef(clientRef);
        transfer.setFlwRef(response.getData().getId());
        transferRepository.save(transfer);

        return new BaseResponse<>(HttpStatus.OK, "Transfer completed", response);
    }

    private User retrieveUserDetails() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    private boolean validatePin(String pin, User user) {
        return passwordEncoder.matches(pin, user.getPin());
    }

    private boolean validateRequestBalance(Double requestAmount) {
        return requestAmount > 0;
    }

    private boolean validateWalletBalance(Double requestAmount,User user){
        Wallet wallet = walletRepository.findWalletByUser(user);
        return wallet.getBalance() >= requestAmount;
    }

    private OtherBankTransferResponse otherBankTransfer(TransferRequest transferRequest, String clientRef){

        OtherBankTransferRequest otherBankTransferRequest = OtherBankTransferRequest.builder()
                .accountBank(transferRequest.getBankCode())
                .accountNumber(transferRequest.getAccountNumber())
                .amount(transferRequest.getAmount())
                .currency("NGN")
                .callbackUrl(callbackUrl)
                .narration(transferRequest.getNarration())
                .reference(clientRef)
                .debitCurrency("NGN")
                .build();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ AUTHORIZATION);

        HttpEntity<OtherBankTransferRequest> request = new  HttpEntity<>(otherBankTransferRequest, headers);

        OtherBankTransferResponse response = restTemplate.exchange(
                Constant.OTHER_BANK_TRANSFER_API,
                HttpMethod.POST,
                request,
                OtherBankTransferResponse.class).getBody();

        return response;
    }

    private Transfer saveTransactions(User user, TransferRequest transferRequest) {

        Wallet wallet = walletRepository.findWalletByUser(user);
        int amount = transferRequest.getAmount().intValue();
        double balance = wallet.getBalance() - amount;
        wallet.setBalance(balance);

        Transfer transfer = Transfer.builder()
                .amount(transferRequest.getAmount())
                .type("OTHER")
                .narration(transferRequest.getNarration())
                .status(Constant.STATUS)
                .senderFullName(user.getFirstName() + " " + user.getLastName())
                .senderAccountNumber(wallet.getAccountNumber())
                .senderBankName(wallet.getBankName())
                .destinationAccountNumber(transferRequest.getAccountNumber())
                .destinationBank(transferRequest.getBankCode())
                .destinationFullName(transferRequest.getAccountName())
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .user(user)
                .build();

        walletRepository.save(wallet);

        return transferRepository.save(transfer);

    }
}
