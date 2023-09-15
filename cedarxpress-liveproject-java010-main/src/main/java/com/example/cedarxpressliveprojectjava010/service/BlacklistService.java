package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.entity.BlacklistToken;
import java.util.Date;

public interface BlacklistService {
    BlacklistToken blackListToken(String token, Date date);
    boolean isTokenBlackListed(String token);
}
