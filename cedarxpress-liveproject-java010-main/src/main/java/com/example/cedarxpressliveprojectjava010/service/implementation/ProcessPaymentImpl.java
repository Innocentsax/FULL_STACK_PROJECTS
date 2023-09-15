package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.request.ProcessPaymentRequest;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletTransactionsRepository;
import com.example.cedarxpressliveprojectjava010.service.ProcessPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProcessPaymentImpl implements ProcessPayment {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionsRepository walletTransactionsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> processPayment(ProcessPaymentRequest processPaymentRequest) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> findWalletOwner = userRepository.findUserByEmail(loggedInUserEmail);
        Optional<Wallet> wallet = walletRepository.findWalletByUserEmail(findWalletOwner.get().getEmail());
        BigDecimal totalAmount = processPaymentRequest.getAmount();
        
      
        String message = "";
        if(wallet.isPresent()){
            if(wallet.get().getBalance().compareTo(totalAmount)==1){
                wallet.get().setBalance(wallet.get().getBalance().subtract(totalAmount));
                walletRepository.save(wallet.get());
                message = "Transaction was successful";
                return new ResponseEntity<>(message, HttpStatus.OK);
            }else{

                message = "Wallet Balance is insufficient";
                return new ResponseEntity<>(message,HttpStatus.OK);
            }

        }else{
            message = "Please Create A wallet";
        }
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);

    }
}
