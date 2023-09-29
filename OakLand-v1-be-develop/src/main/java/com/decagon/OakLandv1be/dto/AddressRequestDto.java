package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {
    private String fullName;
    private String phone;
    private String emailAddress;
    private String street;
    private String state;
    private String country;
}
