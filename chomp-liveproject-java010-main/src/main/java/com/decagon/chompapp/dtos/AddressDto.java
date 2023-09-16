package com.decagon.chompapp.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Getter @Setter
@AllArgsConstructor @RequiredArgsConstructor
@Builder
public class AddressDto {





    @Email
    private String email;

    private String streetAddress;

    private String state;

    private String city;

    //    TODO phone number format
    @Size(max = 15)
    private String phoneNumber;

    @Column(name = "is_shipping_address")
    private Boolean isDefaultShippingAddress;
}
