package com.example.hive.service.implementation;

import com.example.hive.constant.TransactionStatus;
import com.example.hive.dto.request.PayStackPaymentRequest;
import com.example.hive.dto.request.FundWalletRequest;
import com.example.hive.dto.response.PayStackResponse;
import com.example.hive.dto.response.VerifyTransactionResponse;
import com.example.hive.entity.*;
import com.example.hive.enums.Role;
import com.example.hive.exceptions.BadRequestException;
import com.example.hive.exceptions.CustomException;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.*;
import com.example.hive.service.EmailService;
import com.example.hive.service.PayStackService;
import com.example.hive.service.PaymentService;
import com.example.hive.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PayStackService payStackService;
    private final TransactionLogRepository transactionLogRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private final WalletService walletService;

    private final PaymentLogRepository paymentLogRepository;

    private final EscrowWalletRepository escrowWalletRepository;

    @Override
    public PayStackResponse initiatePaymentAndSaveToPaymentLog(FundWalletRequest taskerPaymentRequest, Principal principal) throws Exception {

        // get logged-in user and check if they are a tasker
        User user = verifyAndGetTasker(principal);


        var payStackPaymentRequest = PayStackPaymentRequest.builder()
                    .amount(taskerPaymentRequest.getAmount())
                    .email(user.getEmail())
                    .build();

           PayStackResponse payStackResponse = payStackService.initTransaction(principal, payStackPaymentRequest);

           // save to trasanction log to save the details of the payment
           saveToPaymentLog(payStackResponse, user, taskerPaymentRequest);
           return payStackResponse;

    }




    @Override
    @Transactional
    public VerifyTransactionResponse verifyAndCompletePayment(String reference, Principal principal) throws Exception {


        User tasker = verifyAndGetTasker(principal);

        //check status of transaction first
        PaymentLog paymentLog = paymentLogRepository.findByTransactionReference(reference).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!paymentLog.getTaskerDepositor().equals(tasker))throw new BadRequestException("Illegal transaction");


        if (paymentLog.getTransactionStatus() == TransactionStatus.SUCCESS){
            throw new CustomException("Payment has been completed and verified ");
        }

        VerifyTransactionResponse verifyTransactionResponse = null;
        try {
            verifyTransactionResponse = payStackService.verifyPayment(reference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     log.info("Verified transaction response {}", verifyTransactionResponse.getData().getAmount());

        var status = verifyTransactionResponse.getData().getStatus();
        var amountPaid = BigDecimal.valueOf(verifyTransactionResponse.getData().getAmount());
        var amountToFund = paymentLog.getAmount();

        if (status.equals("failed")){
            paymentLog.setTransactionStatus(TransactionStatus.FAILED);
        return verifyTransactionResponse;
        }


        if (status.equals("success")) {

            if (!(amountPaid.compareTo(amountToFund)==0)){ throw new BadRequestException("Invalid amount was paid for");}
            verifyTransactionResponse.setPaymentLogId(paymentLog.getPaymentLogId());
            paymentLog.setTransactionStatus(TransactionStatus.SUCCESS);
            paymentLogRepository.save(paymentLog);

            walletService.fundTaskerWallet(tasker, amountToFund);
        } else {
            throw new CustomException("Transaction failed");
        }

        return verifyTransactionResponse;

    }

    @Override
    @Transactional
    public VerifyTransactionResponse verifyAndCompletePayment(String reference) throws Exception {


        //check status of transaction first
        PaymentLog paymentLog = paymentLogRepository.findByTransactionReference(reference).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (paymentLog.getTransactionStatus() == TransactionStatus.SUCCESS){
            throw new CustomException("Payment has been completed and verified ");
        }

        VerifyTransactionResponse verifyTransactionResponse = null;
        try {
            verifyTransactionResponse = payStackService.verifyPayment(reference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Verified transaction response {}", verifyTransactionResponse.getData().getAmount());

        var status = verifyTransactionResponse.getData().getStatus();
        var amountPaid = BigDecimal.valueOf(verifyTransactionResponse.getData().getAmount());
        var amountToFund = paymentLog.getAmount();

        if (status.equals("failed")){
            paymentLog.setTransactionStatus(TransactionStatus.FAILED);
            return verifyTransactionResponse;
        }


        if (status.equals("success")) {

            if (!(amountPaid.compareTo(amountToFund)==0)){ throw new BadRequestException("Invalid amount was paid for");}
            verifyTransactionResponse.setPaymentLogId(paymentLog.getPaymentLogId());
            paymentLog.setTransactionStatus(TransactionStatus.SUCCESS);
            paymentLogRepository.save(paymentLog);

            walletService.fundTaskerWallet(paymentLog.getTaskerDepositor(), amountToFund);
        } else {
            throw new CustomException("Transaction failed");
        }

        return verifyTransactionResponse;

    }
    private User verifyAndGetTasker(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getRole().equals(Role.TASKER)){throw new BadRequestException("User is not a tasker");}
        return user;
    }

    private void saveToPaymentLog(PayStackResponse payStackResponse, User user, FundWalletRequest taskerPaymentRequest) {
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setTaskerDepositor(user);
        paymentLog.setTransactionDate(LocalDateTime.now().toString());
        paymentLog.setAmount(BigDecimal.valueOf(taskerPaymentRequest.getAmount()));
        paymentLog.setTransactionReference(payStackResponse.getData().getReference());
        paymentLogRepository.save(paymentLog);
    }
}

