package com.decagon.fintechpaymentapisqd11a.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String bvn;
    private String password;
    private String confirmPassword;
    private String transactionPin;


}
