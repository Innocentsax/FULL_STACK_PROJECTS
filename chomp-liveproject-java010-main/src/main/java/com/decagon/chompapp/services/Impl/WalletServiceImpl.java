package com.decagon.chompapp.services.Impl;



import com.decagon.chompapp.dtos.WalletTransactionRequest;
import com.decagon.chompapp.enums.TransactionType;
import com.decagon.chompapp.exceptions.InsufficientFundsException;
import com.decagon.chompapp.exceptions.WalletCannotBeAccessedException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.models.Wallet;
import com.decagon.chompapp.models.WalletTransaction;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.repositories.WalletRepository;
import com.decagon.chompapp.repositories.WalletTransactionRepository;
import com.decagon.chompapp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static com.decagon.chompapp.enums.TransactionType.FUNDWALLET;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public ResponseEntity<String> fundWalletAccount(WalletTransactionRequest walletTransactionRequest) {

       String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(userEmail);

        if(user.isPresent()) {
            Wallet wallet = walletRepository.findWalletByUser_Email(user.get().getEmail());
            if (wallet != null) {
                double walletBalance = wallet.getWalletBalance();
                wallet.setWalletBalance(walletBalance + walletTransactionRequest.getAmount());
                transactions(walletTransactionRequest, wallet);

                return ResponseEntity.status(HttpStatus.OK).body(walletTransactionRequest.getAmount()+" credited successfully");
            } else {
                Wallet walletSetup = new Wallet();
                walletSetup.setUser(user.get());
                walletSetup.setWalletBalance(walletTransactionRequest.getAmount());
                transactions(walletTransactionRequest, walletSetup);
                return ResponseEntity.status(HttpStatus.OK).body(walletTransactionRequest.getAmount()+" credited successfully");
            }
        }
        else{
            throw new WalletCannotBeAccessedException("wallet cannot be accessed please contact an administrator");
        }
    }

    private void transactions(WalletTransactionRequest walletTransactionRequest, Wallet walletSetup) {
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
        walletTransaction.setTransactionType(FUNDWALLET);
        walletTransaction.setAmount(walletTransactionRequest.getAmount());
        walletTransaction.setWallet(walletSetup);
        walletRepository.save(walletSetup);
        walletTransactionRepository.save(walletTransaction);
    }


    @Override
    public ResponseEntity<String> withdrawFromWallet(WalletTransactionRequest walletTransactionRequest) throws InsufficientFundsException {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(userEmail);

        Wallet wallet = walletRepository.findWalletByUser_Email(user.get().getEmail());
        if(wallet.getWalletBalance() > walletTransactionRequest.getAmount()){
            double balance = wallet.getWalletBalance() - walletTransactionRequest.getAmount();
            wallet.setWalletBalance(balance);
            walletRepository.save(wallet);
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.WITHDRAWAL);
            walletTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
            walletTransaction.setAmount(walletTransactionRequest.getAmount());
            walletTransaction.setWallet(wallet);
            walletRepository.save(wallet);
            walletTransactionRepository.save(walletTransaction);
        }else {
            throw new InsufficientFundsException("insufficient Fund in wallet!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transaction completed successfully \ncurrent wallet balance : "+wallet.getWalletBalance());
    }

    @Override
    public ResponseEntity<String> processPayment(WalletTransactionRequest walletTransactionRequest) throws InsufficientFundsException{

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(userEmail);

        Wallet wallet = walletRepository.findWalletByUser_Email(user.get().getEmail());
        double walletBalance = wallet.getWalletBalance();
        double costOfProduct = walletTransactionRequest.getAmount();
        if(walletBalance >= costOfProduct){

            double newWalletBalance = walletBalance - costOfProduct;
            wallet.setWalletBalance(newWalletBalance);
            walletRepository.save(wallet);
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
            walletTransaction.setTransactionType(TransactionType.MAKEPAYMENT);
            walletTransaction.setAmount(walletTransactionRequest.getAmount());
            walletTransaction.setWallet(wallet);
            walletTransactionRepository.save(walletTransaction);
            return ResponseEntity.status(HttpStatus.OK).body("Transaction completed successfully!");
        }
        else {
            throw new InsufficientFundsException("insufficient Fund in wallet!");
        }
    }

    @Override
    public ResponseEntity<String> checkWalletBalance() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(userEmail);
        Optional<Wallet> wallet = walletRepository.findWalletByUser_Email2(user.get().getEmail());
            Wallet wallet1 = wallet.orElseThrow(() -> new WalletCannotBeAccessedException("Wallet balance cannot be accessed"));
            return ResponseEntity.status(HttpStatus.OK).body("Wallet balance is : " + wallet1.getWalletBalance());
    }

}
