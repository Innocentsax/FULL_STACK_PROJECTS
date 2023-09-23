package com.example.food.util;

import java.util.UUID;

public class PayStackUtil {

    public static final String SECRET_KEY = "sk_test_47a9ce17ad632ed992071105d11ece49fbd5cece";
    public static final String GET_ALL_BANKS = "https://api.paystack.co/bank?currency=NGN";
    public static final String VERIFY_URL = "https://api.paystack.co/transaction/verify/";
    public static final String RESOLVE_BANK = "https://api.paystack.co/bank/resolve";
    public static final String CALLBACK_URL = "http://localhost:8080/wallet/verifyPayment/";
    public static final String INITIALIZE_DEPOSIT = "https://api.paystack.co/transaction/initialize";
    public static final String CREATE_TRANSFER_RECEIPIENT = "https://api.paystack.co/transferrecipient";
    public static final String INITIATE_TRANSFER = "https://api.paystack.co/transfer";
    public static String generateTransactionReference() {
        String timestamp = (System.currentTimeMillis()+"").substring(7);
        String uniqueId = UUID.randomUUID().toString().substring(5, 11);
        return timestamp+uniqueId;
    }
}
