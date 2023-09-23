package com.example.hive.constant;

public class AppConstants {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "updated_date";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    //PayStack
    public static final String PSTK_BASE_URI = "https://api.paystack.co";
    public static final String PSTK_VALIDATE_BANK_URI = "/bank/resolve";
    public static final String PSTK_LIST_BANKS_URI = "/bank";
    public static final String PSTK_TRANSFER_URI = "/transfer";


    public static final String PAY_STACK_SECRET_KEY = "sk_test_cf28086bd2a3cfb580f6868023caee4cc3f2d34c";
    public static final String PSTK_TRANSFER_RECIPIENT_URI = "/transferrecipient";
    public static final String PSTK_VERIFY_TRANSACTION_STATUS_URI = "/transaction/verify/";

}
