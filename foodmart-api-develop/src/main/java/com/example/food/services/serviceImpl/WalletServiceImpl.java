package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.WithDrawalDto;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.WalletResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.services.WalletService;
import com.example.food.services.paystack.PayStackWithdrawalService;
import com.example.food.services.paystack.PaystackPaymentService;
import com.example.food.services.paystack.payStackPojos.Bank;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final PayStackWithdrawalService payStackWithdrawalService;
    private final PaystackPaymentService paystackPaymentService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    public Users getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication).orElseThrow(() -> new RuntimeException("User not authorized"));
    }
    @Override
    public WalletResponse getWalletBalance() {
        WalletResponse walletResponse;
        try {
            Users walletOwner = getLoggedInUser();
            Wallet wallet = walletRepository.findWalletByUser_Email(walletOwner.getEmail());
            walletResponse = WalletResponse.builder()
                    .userName(walletOwner.getFirstName()+" " + walletOwner.getLastName())
                    .walletBalance(wallet.getWalletBalance())
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.SUCCESS, "Wallet Balance");
        } catch (Exception e) {
            log.error("Email not registered, Wallet balance cannot be displayed: {}", e.getMessage());
            walletResponse = WalletResponse.builder()
                    .walletBalance(null)
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.ERROR);
        }
    }

    public ResponseEntity<String> walletWithdrawal(WithDrawalDto withDrawalDto){
        return payStackWithdrawalService.withDrawFromWallet(withDrawalDto.getAccount_number(), withDrawalDto.getBank_code(), withDrawalDto.getAmount());
    }

    public ResponseEntity<String> fundWallet(BigDecimal amount, String transactionType){
        return paystackPaymentService.paystackPayment(amount,transactionType);
    }

    public ResponseEntity<String> verifyPayment(String reference,String transactionType){
        return paystackPaymentService.verifyPayment(reference, transactionType);
    }

    public ResponseEntity<List<Bank>> getAllBanks() {
        return payStackWithdrawalService.getAllBanks();
    }
}