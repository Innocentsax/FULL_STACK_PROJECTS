package com.goCash.services.implementations;

import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.request.LocalTransferRequest;
import com.goCash.dto.response.WalletResponseDto;
import com.goCash.dto.response.WalletVerificationResponse;
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
import com.goCash.services.WalletServices;
import com.goCash.utils.ApiResponse;
import com.goCash.utils.ReferenceGenerator;
import com.goCash.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class WalletServicesImplementation implements WalletServices {
    private final UserRepository userRepository;
    private final Util utils;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public ApiResponse fundWallet() {



        // Saving user by Security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();



        User user = userRepository.findByEmail(loggedInUserUserName)
                .orElse(null);
        if (user == null) {
            log.info("This user does not exist in our database");
            return ApiResponse.builder()
                    .code("01")
                    .message("User not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }


        WalletResponseDto walletResponseDto = WalletResponseDto.builder()
                .accountNumber(user.getWalletAccount().getAccountNumber())
                .accountName(user.getFirstName() + " " + user.getLastName())
                .bankName("Go Cash")
                .build();
        log.info("User details has been fetched");
        return ApiResponse.builder()
                .code("00")
                .message("User details fetched")
                .data(walletResponseDto)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<Double> balanceEnquiry() {
        String loggedInUserName = utils.getLoginUser();
        log.info("check if user exists");
        Optional<User> user = userRepository.findByEmail(loggedInUserName);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        else return new ApiResponse<>("00", "Enquiry Success", user.get().getWalletAccount().getBalance(), HttpStatus.OK);
    }
    @Override
    public ApiResponse walletToWalletTransfer(LocalTransferRequest localTransferRequest){

        String email = utils.getLoginUser();

        WalletAccount senderWallet = walletRepository.findByUserEmail(email)
                .orElseThrow(()-> new WalletNotFoundException("Wallet not found"));
        log.info("checking for the wallet of the user");
        WalletAccount receiverWallet = walletRepository.findWalletAccountByAccountNumber(localTransferRequest.getAccountNumber())
                .orElseThrow(() -> new NoSuchElementException("Receivers account not found"));


        log.info("checking for destination wallet");
        if(senderWallet.getBalance() < localTransferRequest.getAmount()){
            return new ApiResponse("01","insufficient balance",HttpStatus.BAD_REQUEST);
        }
        log.info("creating a new transaction");

        //save sender transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(localTransferRequest.getAmount()));
        transaction.setTransactionType(TransactionType.LOCAL_TRANSFER);
       transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReference(ReferenceGenerator.generateRandomString(10));
        transaction.setUser(userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found")));
        transaction.setProviderMessage("");
        transaction.setMessage("Transfer in process");
        transaction.setStatus(TransactionStatus.PENDING);

        senderWallet.setBalance(senderWallet.getBalance() - localTransferRequest.getAmount());
        receiverWallet.setBalance(receiverWallet.getBalance()+ localTransferRequest.getAmount());

        //update sender transaction
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transaction.setMessage("Transaction successful");

        log.info("saved transaction");
        walletRepository.save(senderWallet);
        log.info("saved user's wallet");
        walletRepository.save(receiverWallet);
        log.info("saved destination wallet");
        Transaction newTransaction = transactionRepository.save(transaction);

        //Saving receiver's transaction
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setAmount(BigDecimal.valueOf(localTransferRequest.getAmount()));
        receiverTransaction.setTransactionType(TransactionType.LOCAL_TRANSFER);
        receiverTransaction.setTransactionDate(LocalDateTime.now());
        receiverTransaction.setReference(ReferenceGenerator.generateRandomString(10));
        receiverTransaction.setUser(userRepository.findByWalletAccountAccountNumber(localTransferRequest.getAccountNumber()).orElseThrow(()->new UserNotFoundException("User not found")));
        transaction.setMessage("Success");
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(receiverTransaction);

        return  ApiResponse.builder()
                .message("successful")
                .code("00")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse verifyLocalAccount(AccountVerificationRequest request) {
        Optional<User> user = userRepository.findByWalletAccountAccountNumber(request.getAccountNumber());
        if(user.isEmpty()){
            return new ApiResponse("01","invalid account number",HttpStatus.BAD_REQUEST);
        }

        WalletVerificationResponse walletVerificationResponse = new WalletVerificationResponse();
        walletVerificationResponse.setFirstName(user.get().getFirstName());
        walletVerificationResponse.setLastName(user.get().getLastName());
        return new ApiResponse("00","success",walletVerificationResponse,HttpStatus.OK);
    }

}








