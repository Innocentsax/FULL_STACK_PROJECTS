package com.goCash.utils.purchase;

import lombok.Data;

@Data

public class Transactions {
    private String status;
    private String product_name;
    private String unique_element;
    private int unit_price;
    private int quantity;
    private String service_verification;
    private String channel;
    private int commission;
    private int total_amount;
    private String discount;
    private String type;
    private String email;
    private String phone;
    private String name;
    private int convinience_fee;
    private int amount;
    private String platform;
    private String method;
    private String transactionId;
    private String created_at;
    private String giftcard_id;


}
