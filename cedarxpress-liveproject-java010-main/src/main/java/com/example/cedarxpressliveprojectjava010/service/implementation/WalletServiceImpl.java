package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.WalletDto;
import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.WalletWithdrawalRequest;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.entity.WalletTransaction;
import com.example.cedarxpressliveprojectjava010.enums.TransactionType;
import com.example.cedarxpressliveprojectjava010.exception.ClientRequestException;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletTransactionsRepository;
import com.example.cedarxpressliveprojectjava010.service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionsRepository walletTransactionsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WalletServiceImpl(UserRepository userRepository, WalletRepository walletRepository, WalletTransactionsRepository walletTransactionsRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionsRepository = walletTransactionsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Wallet> fundWallet(FundWalletRequest fundWalletRequest) {

        Optional<User> user = userRepository.findUserByEmail(fundWalletRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("Kindly input correct email");
        }
        Optional<Wallet> wallet = walletRepository.findWalletByUserEmail(fundWalletRequest.getEmail());
        Wallet wallet2;
        if (wallet.isPresent()) {
            BigDecimal balance = wallet.get().getBalance();
            wallet.get().setBalance(balance.add(fundWalletRequest.getAmount()));
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.FUNDWALLET);
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet.get());
            Wallet wallet1 = walletRepository.save(wallet.get());
            walletTransactionsRepository.save(walletTransaction);
            return new ResponseEntity<>(wallet1, HttpStatus.OK);
        } else {
            Wallet wallet1 = new Wallet();
            wallet1.setUser(user.get());
            wallet1.setBalance(fundWalletRequest.getAmount());
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.FUNDWALLET);
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet1);
            wallet2 = walletRepository.save(wallet1);
            walletTransactionsRepository.save(walletTransaction);
        }

        return new ResponseEntity<>(wallet2, HttpStatus.OK );
    }



    @Override
    public ResponseEntity<WalletDto> checkBalance(long id) {
        User customer = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Wallet wallet = walletRepository.findWalletByUserEmail(customer.getEmail()).get();
        WalletDto walletDto = WalletDto.builder()
                .currentBalance(wallet.getBalance())
                .build();
        return ResponseEntity.ok(walletDto);
    }

    @Override
    public ResponseEntity<Wallet> walletWithdrawal(WalletWithdrawalRequest walletWithdrawalRequest) {
        Wallet wallet = walletRepository.findWalletByUserEmail(walletWithdrawalRequest.getEmail()).orElseThrow(()-> new NotFoundException("Kindly input correct email"));
        if (walletWithdrawalRequest.getAmount().compareTo(wallet.getBalance()) > 0) {
            throw new ClientRequestException("Insufficient funds");
        } else {
            BigDecimal balance = wallet.getBalance();
            wallet.setBalance(balance.subtract(walletWithdrawalRequest.getAmount()));
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.WITHDRAWAL);
            walletTransaction.setAmount(walletWithdrawalRequest.getAmount());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet);
            walletTransactionsRepository.save(walletTransaction);
            walletRepository.save(wallet);
        }
        return ResponseEntity.ok(wallet);
    }
}



