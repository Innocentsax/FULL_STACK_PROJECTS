package com.goCash.utils;

import com.goCash.entities.Transaction;
import com.goCash.enums.TransactionStatus;
import com.goCash.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResponseManager {

    private final TransactionRepository transactionRepository;

    public static final String INSUFFICIENT_FUNDS = "INSUFFICIENT FUNDS";
    public static final String RECHARGE_AMOUNT_BELOW_MINIMUM = "AMOUNT BELOW MINIMUM";
    public static final String RECHARGE_AMOUNT_ABOVE_MAXIMUM = "AMOUNT ABOVE MAXIMUM";

    public ApiResponse airtimePurchaseValidation(double accountBalance, double requestAmount, Transaction transact){

        ApiResponse response = new ApiResponse<>();

        if (accountBalance < requestAmount) {
            log.error("User funds not sufficient");

            transact.setMessage("018");
            transact.setStatus(TransactionStatus.FAILED);
            transact.setProviderMessage(INSUFFICIENT_FUNDS);
            transactionRepository.save(transact);

           return response.builder()
                    .message(INSUFFICIENT_FUNDS)
                    .code("018")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        if (requestAmount < 100) {
            log.error("Amount too small");

            transact.setMessage("013");
            transact.setStatus(TransactionStatus.FAILED);
            transact.setProviderMessage(RECHARGE_AMOUNT_BELOW_MINIMUM);
            transactionRepository.save(transact);

            return response.builder()
                    .message(RECHARGE_AMOUNT_BELOW_MINIMUM)
                    .code("013")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        if (requestAmount > 6000) {
            log.error("Amount above max");

            transact.setMessage("017");
            transact.setStatus(TransactionStatus.FAILED);
            transact.setProviderMessage(RECHARGE_AMOUNT_ABOVE_MAXIMUM);
            transactionRepository.save(transact);

            return response.builder()
                    .message(RECHARGE_AMOUNT_ABOVE_MAXIMUM)
                    .code("017")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return response.builder()
                .code("000")
                .build();
    }
}
