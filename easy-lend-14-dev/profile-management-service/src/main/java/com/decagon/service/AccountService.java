package com.decagon.service;

public interface AccountService {
    Object getBankCodeAndSend(String bankName, String accountNumber,String authorizationHeader);

}
