package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Address;
import com.decagon.OakLandv1be.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String date_of_birth;
    private String phoneNumber;
    private Boolean verificationStatus;
    private String address;
}
