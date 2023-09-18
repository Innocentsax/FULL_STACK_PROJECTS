package com.example.decapay.pojos.responseDtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Data
@Builder
@Getter
@Setter
public class LoginResponseDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String imagePath;
    private String email;
    private String phoneNumber;
    private String token;
}
