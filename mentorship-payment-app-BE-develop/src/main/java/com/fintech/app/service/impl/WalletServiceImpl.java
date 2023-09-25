package com.fintech.app.service.impl;

import com.fintech.app.model.*;
import com.fintech.app.repository.TransferRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.request.FlwWalletRequest;
import com.fintech.app.request.FundWalletRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.FlwVirtualAccountResponse;
import com.fintech.app.response.WalletResponse;
import com.fintech.app.service.WalletService;
import com.fintech.app.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    @Value("${FLW_SECRET_KEY}")
    private String AUTHORIZATION;

    @Override
    public Wallet createWallet(FlwWalletRequest walletRequest) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer "+ AUTHORIZATION);

        FlwWalletRequest payload = generatePayload(walletRequest);

        HttpEntity<FlwWalletRequest> request = new  HttpEntity<>(payload, headers);

        FlwVirtualAccountResponse body = restTemplate.exchange(
                Constant.CREATE_VIRTUAL_ACCOUNT_API,
                HttpMethod.POST,
                request,
                FlwVirtualAccountResponse.class).getBody();

        assert body != null;
        return Wallet.builder()
                .accountNumber(body.getData().getAccountNumber())
                .balance(Double.parseDouble(body.getData().getAmount()))
                .bankName(body.getData().getBankName())
                .txRef(payload.getTxRef())
                .build();
    }

    @Override
    public BaseResponse<WalletResponse> fetchUserWallet() {
        String loggedInUsername =  SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findUserByEmail(loggedInUsername);
        if (user == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User not found", null);
        }
        Wallet wallet = walletRepository.findWalletByUser(user);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd-MMMM-yyyy HH:mm");
        WalletResponse response = WalletResponse.builder()
                .walletId(wallet.getId())
                .accountNumber(wallet.getAccountNumber())
                .balance(String.format("\u20a6%,.2f",wallet.getBalance()))
                .bankName(wallet.getBankName())
                .createdAt(dateFormat.format(wallet.getCreatedAt()))
                .updatedAt(dateFormat.format(wallet.getModifyAt()))
                .build();
        return new BaseResponse<>(HttpStatus.OK, "User wallet retrieved", response);
    }

    @Transactional
    @Override
    public BaseResponse<?> fundWallet(FundWalletRequest request) {

        log.info(request.toString());

        Long flwRef = request.getData().getId();
        String txStatus = request.getData().getStatus();
        double txAmount = request.getData().getAmount();
        String txRef = request.getData().getTxRef();

        Wallet wallet = walletRepository.findWalletByTxRef(txRef);
        if(wallet == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "wallet not found", null);
        }

        Transfer check = transferRepository.findByFlwRef(flwRef).orElse(null);
        if (check != null) {
            if (check.getStatus().equalsIgnoreCase("successful")) {
                return new BaseResponse<>(HttpStatus.OK, "Wallet credited already", null);
            }
//            check.setStatus(txStatus);
//            if (check.getStatus().equalsIgnoreCase("successful")){
//                wallet.setBalance(wallet.getBalance() + txAmount);
//                walletRepository.save(wallet);
//                return new BaseResponse<>(HttpStatus.OK, "Wallet with account_no " + wallet.getAccountNumber() + " credited with N" + txAmount, null);
//            }
//            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, "wallet fund unsuccessful", null);
        }

        if (txStatus.equalsIgnoreCase("successful")) {
            wallet.setBalance(wallet.getBalance() + txAmount);
            walletRepository.save(wallet);
            User user = wallet.getUser();
            Transfer transfer = Transfer.builder()
                    .flwRef(request.getData().getId())
                    .amount(txAmount)
                    .status(txStatus)
                    .destinationBank(wallet.getBankName())
                    .destinationFullName(user.getFirstName() + user.getLastName())
                    .destinationAccountNumber(wallet.getAccountNumber())
                    .senderFullName(request.getData().getCustomer().getName())
                    .senderBankName("WALLET FUND")
                    .senderAccountNumber("nil")
                    .type("WALLET_FUND")
                    .user(user)
                    .clientRef(UUID.randomUUID().toString())
                    .createdAt(LocalDateTime.now())
                    .build();
            transferRepository.save(transfer);
        }


        return new BaseResponse<>(HttpStatus.OK, "Wallet with account_no " + wallet.getAccountNumber() + " credited with N" + txAmount, null);
    }

    private FlwWalletRequest generatePayload(FlwWalletRequest walletRequest) throws JSONException {
        FlwWalletRequest payload = new FlwWalletRequest();
        payload.setEmail(walletRequest.getEmail());
        payload.set_permanent(true);
        payload.setBvn(walletRequest.getBvn());
        payload.setPhonenumber(walletRequest.getPhonenumber());
        payload.setFirstname(walletRequest.getFirstname());
        payload.setLastname(walletRequest.getLastname());
        payload.setTxRef(UUID.randomUUID().toString());
        payload.setNarration(walletRequest.getFirstname() + " " + walletRequest.getLastname());

        return payload;
    }
}
