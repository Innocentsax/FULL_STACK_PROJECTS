package com.decagon.OakLandv1be.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponseDto {

    private Long id;
    private String fullName;
    private String street;
    private String state;
    private String country;
    private String phone;
    private String emailAddress;
    private Boolean isDefault;
}
