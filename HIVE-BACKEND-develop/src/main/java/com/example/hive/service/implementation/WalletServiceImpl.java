package com.example.hive.service.implementation;

import com.example.hive.constant.TransactionStatus;
import com.example.hive.constant.TransactionType;
import com.example.hive.dto.response.TransactionResponse;
import com.example.hive.entity.*;
import com.example.hive.dto.response.WalletResponseDto;
import com.example.hive.enums.Role;
import com.example.hive.exceptions.CustomException;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.EscrowWalletRepository;
import com.example.hive.repository.TransactionLogRepository;
import com.example.hive.repository.UserRepository;
import com.example.hive.repository.WalletRepository;
import com.example.hive.service.WalletService;
import com.example.hive.utils.event.SuccessfulCreditEvent;
import com.example.hive.utils.event.WalletFundingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final TransactionLogRepository transactionLogRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final EscrowWalletRepository escrowWalletRepository;

    private final ModelMapper modelMapper;

    @Override
    public boolean creditDoerWallet(User doer, BigDecimal creditAmount){

        log.info("Crediting doer wallet{}", doer.getFullName()) ;
        //check role of user
        if (!doer.getRole().equals(Role.DOER)) {
            throw new CustomException("User is not a doer");
        }
        else {
            //check if user has a wallet
         Wallet wallet =  walletRepository.findByUser(doer).orElseThrow(() -> new CustomException("User does not have a wallet"));
            log.info("I found wallet balance of {}", wallet.getAccountBalance()) ;

            if (wallet.getAccountBalance() == null) {wallet.setAccountBalance(creditAmount);}

             //credit wallet
            else { wallet.setAccountBalance(wallet.getAccountBalance().add(creditAmount));}
            log.info("NOW I found wallet balance of {}", wallet.getAccountBalance()) ;

            TransactionLog transactionLog = new TransactionLog ();
            transactionLog.setAmount(creditAmount);
            transactionLog.setUser(doer);
            transactionLog.setTransactionType(TransactionType.DEPOSIT);
            transactionLog.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionLog.setTransactionDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));

            transactionLogRepository.save(transactionLog);
                eventPublisher.publishEvent(new SuccessfulCreditEvent(doer, transactionLog));
                eventPublisher.publishEvent(new WalletFundingEvent(this, doer, creditAmount));

                return true;

        }

    }


    @Override
    public void withdrawFromWalletBalance(User user, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new CustomException("User does not have a wallet"));
        if (wallet.getAccountBalance().compareTo(amount) < 0) {
            throw new CustomException("Insufficient funds");
        }
        wallet.setAccountBalance(wallet.getAccountBalance().subtract(amount));
        walletRepository.save(wallet);
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setAmount(amount);
        transactionLog.setUser(user);
        transactionLog.setTransactionType(TransactionType.WITHDRAW);
        transactionLog.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionLog.setTransactionDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
        transactionLogRepository.save(transactionLog);
    }
    @Override
    public WalletResponseDto getWalletByUser(Principal principal) {

        //get the user from the princial
        User user = userRepository.findByEmail(principal.getName()).get();
        Wallet getWallet = walletRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Wallet not found"));
        return new WalletResponseDto(getWallet.getAccountBalance());
    }

    @Override
    public boolean fundTaskerWallet(User tasker, BigDecimal amountToFund) {
        log.info("Crediting tasker wallet{}", tasker.getFullName()) ;
        //check role of user
        if (!tasker.getRole().equals(Role.TASKER)) {
            throw new CustomException("User is not a tasker");
        }
        else {
            //check if user has a wallet
            Wallet wallet =  walletRepository.findByUser(tasker).orElseThrow(() -> new CustomException("User does not have a wallet"));
            log.info("I found wallet balance of {}", wallet.getAccountBalance()) ;

            if (wallet.getAccountBalance() == null) {wallet.setAccountBalance(amountToFund);}

            //credit wallet
            else { wallet.setAccountBalance(wallet.getAccountBalance().add(amountToFund));}
            log.info("NOW I found wallet balance of {}", wallet.getAccountBalance()) ;

            TransactionLog transactionLog = new TransactionLog ();
            transactionLog.setAmount(amountToFund);
            transactionLog.setUser(tasker);
            transactionLog.setTransactionType(TransactionType.DEPOSIT);
            transactionLog.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionLog.setTransactionDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));

            transactionLogRepository.save(transactionLog);
            walletRepository.save(wallet);
            eventPublisher.publishEvent(new SuccessfulCreditEvent(tasker, transactionLog));
            eventPublisher.publishEvent(new WalletFundingEvent(this, tasker, amountToFund));
            return true;

        }
    }

    @Override
    public boolean debitTaskerWalletToEscrow(Wallet wallet, BigDecimal amount) {
        log.info("Debiting tasker wallet{}", wallet.getUser().getFullName()) ;
        //check role of user
        if (!wallet.getUser().getRole().equals(Role.TASKER)) {
            throw new CustomException("User is not a tasker role");
        }
        else {
            //check if user has a wallet
            Wallet getWallet =  walletRepository.findByUser(wallet.getUser()).orElseThrow(() -> new CustomException("User does not have a wallet"));
            log.info("I found wallet balance of {}", getWallet.getAccountBalance()) ;

            if (getWallet.getAccountBalance() == null) {throw new CustomException("Insufficient funds");}

            //debit wallet for task creation
            else { getWallet.setAccountBalance(getWallet.getAccountBalance().subtract(amount));}
            log.info("NOW I found wallet balance of {}", getWallet.getAccountBalance()) ;

            walletRepository.save(wallet);
            return true;

        }
    }

    @Override
    public boolean refundTaskerFromEscrowWallet(Task task) {
        User tasker = task.getTasker();
        EscrowWallet escrowWallet = task.getEscrowWallet();
       if ( fundTaskerWallet(tasker, escrowWallet.getEscrowAmount())){
           escrowWallet.setEscrowAmount(new BigDecimal(0));
           escrowWalletRepository.save(escrowWallet);
           return true;
    }
        return false;
    }

    @Override
    public List<TransactionResponse> getWalletHistory(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<TransactionLog> transactionLogs = transactionLogRepository.findAllByUserAndTransactionStatus(user, TransactionStatus.SUCCESS);
        return transactionLogs.stream().map(transactionLog -> modelMapper.map(transactionLog, TransactionResponse.class)).collect(Collectors.toList());
    }
}
