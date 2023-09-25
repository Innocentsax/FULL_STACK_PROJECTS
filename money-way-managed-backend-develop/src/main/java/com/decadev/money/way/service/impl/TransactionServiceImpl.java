package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.request.CashTransferRequest;
import com.decadev.money.way.dto.request.TransactionHistoryRequest;
import com.decadev.money.way.dto.response.BeneficiaryResponse;
import com.decadev.money.way.dto.response.TransactionHistoryResponse;
import com.decadev.money.way.enums.Status;
import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.enums.TransactionType;
import com.decadev.money.way.event.EventListener;
import com.decadev.money.way.exception.TransactionException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.Beneficiary;
import com.decadev.money.way.model.Transaction;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.Wallet;
import com.decadev.money.way.repository.BeneficiaryRepository;
import com.decadev.money.way.repository.TransactionRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.repository.WalletRepository;
import com.decadev.money.way.service.TransactionService;
import com.decadev.money.way.utils.SecurityUtils;
import com.decadev.money.way.utils.TransactionReferenceGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SecurityUtils securityUtils;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventListener eventListener;
    private final WalletRepository walletRepository;

    @Override
 public ResponseEntity<List<TransactionHistoryResponse>> transactionHistory(TransactionHistoryRequest request){
     User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     Long userID = loggedInUser.getId();
     String endDate = Objects.isNull(request.getEndDate())?
             LocalDateTime.now().toString():
             LocalDateTime.of(LocalDate.parse(request.getEndDate()),LocalTime.MIDNIGHT).toString();

    String startDate = Objects.isNull(request.getStartDate())?
            LocalDateTime.parse(endDate).minus(Period.ofMonths(6)).toString():
            LocalDateTime.of(LocalDate.parse(request.getStartDate()),LocalTime.MIDNIGHT).toString();

     BigDecimal minAmount = request.getMinAmount();
     BigDecimal maxAmount = request.getMaxAmount();
     Sort sort = request.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())?
             Sort.by(request.getSortBy()).ascending():
             Sort.by(request.getSortBy()).descending();
     Pageable pageable = PageRequest.of(request.getPageNo(),request.getPageSize(),sort);

     Page<Transaction> userTransactions = transactionRepository.findAllByUserIdAndAmountRangeOptional(userID,
             startDate,endDate,minAmount,maxAmount,pageable);

     List<TransactionHistoryResponse> transactionList = userTransactions.stream().map(this::mappedToResponse)
             .collect(Collectors.toList());

     return ResponseEntity.status(HttpStatus.OK).body(transactionList);
 }

    @Override
    public String verifyRecipientDetails(String request) throws UserNotFoundException {
        userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername()).orElseThrow(()->new UserNotFoundException("User not found"));

        User recipient = (User) userRepository.findByWallet(walletRepository.findByAccountNumber(request)).orElseThrow(()-> new TransactionException("User not found"));


        if(!recipient.getEnabled()) throw new TransactionException("User account is not yet enabled");
        if(!recipient.getWallet().isActive()) throw new TransactionException("Beneficiary wallet not active");

        return recipient.getFirstName()+" "+recipient.getLastName();
    }

    @Override
    @Transactional
    public String transferToWallet(CashTransferRequest request) throws UserNotFoundException {
        User fundSender = userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername()).orElseThrow(()->new UserNotFoundException("User not found"));

        User recipient = (User) userRepository.findByWallet(walletRepository.findByAccountNumber(request.getAccountNumber())).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(fundSender.getWallet().getAccountNumber().equals(request.getAccountNumber())) throw new TransactionException("This transaction is not allowed");

        if(!passwordEncoder.matches(request.getPin(), fundSender.getPin())) throw new TransactionException("Incorrect transaction PIN !");

        String fundSenderBalance= debitFundSenderWallet(request.getAmount(), fundSender.getWallet());

        if(fundSenderBalance.equalsIgnoreCase("Insufficient balance")) throw new TransactionException("Transaction cannot be completed due to insufficient balance");

        String recipientBalance = creditRecipientWallet(request.getAmount(), recipient.getWallet());

        if(request.isSaveBeneficiary()) saveBeneficiary(recipient, fundSender);

        fundSender.getWallet().setAccountBalance(fundSenderBalance);
        recipient.getWallet().setAccountBalance(recipientBalance);


       String transactionId = TransactionReferenceGenerator.generateReference();



        Transaction debitTransaction = Transaction.builder()
                .transactionType(TransactionType.CASH_TRANSFER)
                .category(TransactionCategory.DEBIT)
                .cashTransferType("Local transfer")
                .user(fundSender)
                .date(LocalDateTime.now())
                .description(request.getDescription())
                .beneficiary(recipient.getFirstName()+" "+recipient.getLastName())
                .amount(request.getAmount())
                .status(Status.SUCCESS)
                .referenceId(transactionId)
                .build();

        eventListener.sendTransactionDetails(debitTransaction);

        Transaction creditTransaction = Transaction.builder()
                .transactionType(TransactionType.CASH_TRANSFER)
                .category(TransactionCategory.CREDIT)
                .cashTransferType("Local transfer")
                .user(recipient)
                .date(LocalDateTime.now())
                .description(request.getDescription())
                .sender(fundSender.getFirstName()+" "+fundSender.getLastName())
                .amount(request.getAmount())
                .status(Status.SUCCESS)
                .referenceId(transactionId)
                        .build();

        eventListener.sendTransactionDetails(creditTransaction);

        fundSender.getTransactions().add(transactionRepository.save(debitTransaction));
        recipient.getTransactions().add(transactionRepository.save(creditTransaction));

        userRepository.saveAll(Arrays.asList(fundSender, recipient));

        return "Transaction successful";
    }

    @Override
    public List<BeneficiaryResponse> getBeneficiaries(String transactionType) throws UserNotFoundException {
        User loggedInUser = userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Beneficiary> beneficiaries;
        if (transactionType.equalsIgnoreCase("cash-transfer")) {
            beneficiaries = beneficiaryRepository.findAllByUserAndTransactionType(loggedInUser, TransactionType.CASH_TRANSFER);
        } else if (transactionType.equalsIgnoreCase("airtime")) {
            beneficiaries = beneficiaryRepository.findAllByUserAndTransactionType(loggedInUser, TransactionType.AIRTIME);
        } else if (transactionType.equalsIgnoreCase("data")) {
            beneficiaries = beneficiaryRepository.findAllByUserAndTransactionType(loggedInUser, TransactionType.DATA);
        } else {
            return Collections.emptyList(); // Return an empty list if the transactionType is invalid
        }

        return beneficiaries.stream()
                .map(this::mapToBeneficiaryResponse)
                .collect(Collectors.toList());
    }
    public TransactionHistoryResponse mappedToResponse(Transaction transaction) {
     return TransactionHistoryResponse.builder()
             .id(transaction.getId())
             .amount(transaction.getAmount())
             .date(transaction.getDate())
             .description(transaction.getDescription())
             .referenceId(transaction.getReferenceId())
             .status(transaction.getStatus())
             .type(transaction.getCashTransferType())
             .beneficiary(transaction.getBeneficiary())
             .user(transaction.getUser())
             .build();
    }

    private BeneficiaryResponse mapToBeneficiaryResponse(Beneficiary beneficiary){
        return BeneficiaryResponse.builder()
                .accountBank(beneficiary.getCashTransferType())
                .accountNumber(beneficiary.getAccountNumber())
                .name(beneficiary.getName())
                .build();
    }

    private void saveBeneficiary(User recipient, User user) {
        List<Beneficiary> checkBeneficiary = beneficiaryRepository.findAllByUserAndTransactionType(user, TransactionType.CASH_TRANSFER);

        // Check if the beneficiary already exists
        boolean beneficiaryExists = checkBeneficiary.stream()
                .anyMatch(beneficiary -> beneficiary.getAccountNumber().equals(recipient.getWallet().getAccountNumber()));

        if (beneficiaryExists) {
            // Throw a BeneficiaryAlreadyExistsException
            throw new TransactionException("Beneficiary already exists for the user.");
        } else {
            // Save the new beneficiary
            Beneficiary beneficiary = Beneficiary.builder()
                    .transactionType(TransactionType.CASH_TRANSFER)
                    .accountNumber(recipient.getWallet().getAccountNumber())
                    .cashTransferType("Local transfer")
                    .name(recipient.getFirstName() + " " + recipient.getLastName())
                    .bank(recipient.getWallet().getBankName())
                    .user(user)
                    .build();
            beneficiaryRepository.save(beneficiary);
        }
    }

    public String debitFundSenderWallet(String amount, Wallet wallet){
        BigDecimal debitAmount = new BigDecimal(amount);
        BigDecimal balance = new BigDecimal(wallet.getAccountBalance());
        if(balance.compareTo(debitAmount) < 0)throw new TransactionException("Insufficient balance");
        BigDecimal newBalance = balance.subtract(debitAmount);
        return newBalance.toString();
    }

    public String creditRecipientWallet(String amount, Wallet wallet){
        BigDecimal creditAmount = new BigDecimal(amount);
        BigDecimal balance = new BigDecimal(wallet.getAccountBalance());
        BigDecimal newBalance = balance.add(creditAmount);
        return newBalance.toString();
    }

}

