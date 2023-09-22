package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.enums.Transactiontype;
import com.decagon.fintechpaymentapisqd11a.exceptions.ErrorException;
import com.decagon.fintechpaymentapisqd11a.exceptions.IncorrectDetailsException;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.FlwBank;
import com.decagon.fintechpaymentapisqd11a.models.Transaction;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.TransactionRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import com.decagon.fintechpaymentapisqd11a.request.ExternalBankTransferRequest;
import com.decagon.fintechpaymentapisqd11a.request.FlwOtherBankTransferRequest;
import com.decagon.fintechpaymentapisqd11a.request.FlwResolveAccountRequest;
import com.decagon.fintechpaymentapisqd11a.response.FlwGetAllBanksResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwOtherBankTransferResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwResolveAccountDetails;
import com.decagon.fintechpaymentapisqd11a.services.TransferService;
import com.decagon.fintechpaymentapisqd11a.util.Constant;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Builder
@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<FlwBank> getAllBanks() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwGetAllBanksResponse> httpEntity = new HttpEntity<>(null, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        FlwGetAllBanksResponse getAllBanksResponse = restTemplate.exchange(
                Constant.GET_ALL_BANKS,
                HttpMethod.GET,
                httpEntity,
                FlwGetAllBanksResponse.class
        ).getBody();
        return getAllBanksResponse.getData();
    }

    @Override
    public FlwResolveAccountDetails resolveAccount(FlwResolveAccountRequest resolveAccountRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwResolveAccountRequest> accountRequestHttpEntity = new HttpEntity<>(resolveAccountRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        FlwResolveAccountDetails resolveAccountDetails = restTemplate.exchange(
                Constant.RESOLVE_ACCOUNT_DETAILS,
                HttpMethod.POST,
                accountRequestHttpEntity,
                FlwResolveAccountDetails.class
        ).getBody();

        return resolveAccountDetails;
    }

    @Override
    public FlwOtherBankTransferResponse initiateOtherBankTransfer(ExternalBankTransferRequest transferRequest) {
        User user2 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersRepository.findByEmail(user2.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));

        if(!validatePin(transferRequest.getPin(), user)){
            throw new IncorrectDetailsException("Incorrect pin");
        }
        if(!validateRequestBalance(transferRequest.getAmount())){
            throw new IncorrectDetailsException("Transfer must be above zero naira");
        }
        if(!validateWalletBalance(transferRequest.getAmount(), user)){
            throw new IncorrectDetailsException("Insufficient balance");
        }

        String clientRef = UUID.randomUUID().toString();

        FlwOtherBankTransferResponse flwOtherBankTransferResponse = otherBankTransfer(transferRequest, clientRef);

        if(!flwOtherBankTransferResponse.getStatus().equalsIgnoreCase("success")){
            throw new ErrorException("An error occurred");
        }

        Transaction transaction = saveTransactions(user, transferRequest);
        transaction.setClientRef(clientRef);
        transaction.setFlwRef(flwOtherBankTransferResponse.getData().toString());
        transactionRepository.save(transaction);

        return flwOtherBankTransferResponse;
    }

    @Override
    public Users retrieveUserDetails() {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersRepository.findByEmail(user1.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return user;
    }

    @Override
    public boolean validatePin(String pin, Users users) {
        return passwordEncoder.matches(pin, users.getTransactionPin());
    }

    @Override
    public boolean validateRequestBalance(BigDecimal requestAmount) {
        int result = requestAmount.compareTo(BigDecimal.valueOf(0));
        return result > 0;
    }

    @Override
    public boolean validateWalletBalance(BigDecimal requestAmount, Users users) {
        Wallet wallet = walletRepository.findWalletByUsers(users);
        int result = wallet.getBalance().compareTo(requestAmount.doubleValue());
        if(result == 0 || result == 1) return true;
        return false;
    }


    @Override
    public FlwOtherBankTransferResponse otherBankTransfer(ExternalBankTransferRequest transferRequest, String clientRef) {
        FlwOtherBankTransferRequest otherBankTransferRequest = FlwOtherBankTransferRequest.builder()
                .accountBank(transferRequest.getBankCode())
                .accountNumber(transferRequest.getAccountNumber())
                .amount(transferRequest.getAmount().doubleValue())
                .currency("NGN")
                .narration(transferRequest.getNarration())
                .reference(clientRef)
                .debitCurrency("NGN")
                .callbackUrl("Mumini")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + Constant.AUTHORIZATION);

        HttpEntity<FlwOtherBankTransferRequest> requestHttpEntity = new HttpEntity<>(otherBankTransferRequest, headers);

        RestTemplate restTemplate = new RestTemplate();

        FlwOtherBankTransferResponse otherBankTransferResponse = restTemplate.exchange(
                Constant.OTHER_BANK_TRANSFER,
                HttpMethod.POST,
                requestHttpEntity,
                FlwOtherBankTransferResponse.class
        ).getBody();

        return otherBankTransferResponse;
    }

    @Override
    public Transaction saveTransactions(Users users, ExternalBankTransferRequest transferRequest) {
        String clientReference = UUID.randomUUID().toString();

        User user2 = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersRepository.findByEmail(user2.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        Wallet wallet = walletRepository.findWalletByUsers(user);
        Double amount = transferRequest.getAmount().doubleValue();
        Double balance = wallet.getBalance() - amount;
        wallet.setBalance(balance);

        Transaction transaction = Transaction.builder()
                .amount(transferRequest.getAmount())
                .clientRef(clientReference)
                .flwRef(clientReference)
                .narration(transferRequest.getNarration())
                .destinationAccountNumber(transferRequest.getAccountNumber())
                .destinationBank(transferRequest.getBankCode())
                .users(user)
                .sourceAccountNumber(wallet.getAcctNumber())
                .transactiontype(Transactiontype.DEBIT)
                .wallet(wallet)
                .build();

        walletRepository.save(wallet);

        return transactionRepository.save(transaction);
    }
}