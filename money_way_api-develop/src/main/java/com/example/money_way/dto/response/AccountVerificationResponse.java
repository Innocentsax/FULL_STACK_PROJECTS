package com.example.money_way.dto.response;

import lombok.Data;



@Data
public class AccountVerificationResponse<T>{
    private String code;
    private T content;

}
