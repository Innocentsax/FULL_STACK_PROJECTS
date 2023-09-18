package com.example.decapay.pojos.responseDtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
