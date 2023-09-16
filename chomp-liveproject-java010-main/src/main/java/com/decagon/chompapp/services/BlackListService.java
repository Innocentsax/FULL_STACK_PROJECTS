package com.decagon.chompapp.services;


import com.decagon.chompapp.models.BlackListedToken;

public interface BlackListService {

    BlackListedToken blackListToken(String  token);

//    BlackListedToken getToken(String  token);

    boolean tokenExist(String token);
}
