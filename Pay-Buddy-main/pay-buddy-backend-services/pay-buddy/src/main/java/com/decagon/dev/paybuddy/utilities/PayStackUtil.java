package com.decagon.dev.paybuddy.utilities;

import java.util.UUID;

public class PayStackUtil {

//    public static final String SECRET_KEY = "sk_test_0acdf6faac7e1c4bac4f9b92ac8c0057982b993a";
//    public static final String SECRET_KEY = "sk_test_47a9ce17ad632ed992071105d11ece49fbd5cece";
    public static final String SECRET_KEY = "sk_test_e32d250b1f66f51083d0220f850fdd5f56e992db";
    public static final String GET_ALL_BANKS = "https://api.paystack.co/bank?currency=NGN";
    public static final String VERIFY_URL = "https://api.paystack.co/transaction/verify/";
    public static final String RESOLVE_BANK = "https://api.paystack.co/bank/resolve";
    public static final String CALLBACK_URL = "http://localhost:8080/api/v1/wallet/verifyPayment/";
    public static final String INITIALIZE_DEPOSIT = "https://api.paystack.co/transaction/initialize";
    public static final String CREATE_TRANSFER_RECEIPIENT = "https://api.paystack.co/transferrecipient";
    public static final String INITIATE_TRANSFER = "https://api.paystack.co/transfer";
    public static String generateTransactionReference() {
        String timestamp = (System.currentTimeMillis()+"").substring(7);
        String uniqueId = UUID.randomUUID().toString().substring(5, 11);
        return timestamp+uniqueId;
    }
}
