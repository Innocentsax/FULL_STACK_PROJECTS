package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface PasswordUpdateService {
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
