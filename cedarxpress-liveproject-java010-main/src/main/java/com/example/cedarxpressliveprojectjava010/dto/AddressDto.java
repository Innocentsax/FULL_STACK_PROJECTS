package com.example.cedarxpressliveprojectjava010.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String addressType;
    private String state;
    private String city;
    private String address;
    private Integer zipCode;
    private String phoneNumber;
    private Boolean isDefault;
}
