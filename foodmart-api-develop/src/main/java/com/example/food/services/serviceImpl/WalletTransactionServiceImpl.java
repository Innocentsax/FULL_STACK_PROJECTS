package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.WalletTransactionDto;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.model.WalletTransaction;
import com.example.food.pojos.WalletTransactionResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.services.WalletTransactionService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    @Override
    public WalletTransactionResponse viewWalletTransaction() {
        Wallet userWallet;
        WalletTransactionResponse walletTransactionResponse;
        String userEmail = userUtil.getAuthenticatedUserEmail();
        Optional<Users> loggedUser = userRepository.findByEmail(userEmail);

        if(loggedUser.isPresent()){
            userWallet = loggedUser.get().getWallet();
            List<WalletTransaction> walletTransaction = userWallet.getWalletTransactions();
            List<WalletTransactionDto> walletTransactionList = new ArrayList<>();
            WalletTransactionDto walletTransactionDto = new WalletTransactionDto();

            for(WalletTransaction walletTransaction1: walletTransaction){
                walletTransactionDto.setTransactionReference(walletTransaction1.getTransactionReference());
                walletTransactionDto.setTransactionDate(walletTransaction1.getTransactionDate());
                walletTransactionDto.setAmount(walletTransaction1.getAmount());
                walletTransactionDto.setTransactionType(walletTransaction1.getTransactionType());
                walletTransactionList.add(walletTransactionDto);
                walletTransactionDto = new WalletTransactionDto();
            }
        walletTransactionResponse= WalletTransactionResponse.builder()
                .walletTransactions(walletTransactionList)
                .build();
           return responseCodeUtil.updateResponseData(walletTransactionResponse, ResponseCodeEnum.SUCCESS);
        }
        return responseCodeUtil.updateResponseData(new WalletTransactionResponse(),ResponseCodeEnum.ERROR);
    }
}
