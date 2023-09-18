package com.example.decapay.pojos.responseDtos;

import com.example.decapay.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter

public class TokenVerificationResponse {
    private String token;
    private Status status;
    private String email;
}
