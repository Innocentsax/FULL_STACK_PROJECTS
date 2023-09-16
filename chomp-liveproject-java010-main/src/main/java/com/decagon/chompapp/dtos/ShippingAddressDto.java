package com.decagon.chompapp.dtos;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingAddressDto {

    @Size(min = 2, max=64, message = "lastname must be at least two letter long")
    @Column(nullable = false)
    private String recipientName;


    private String streetAddress;

    @Email
    private String email;

    private String state;

    private String city;



    @Size(max = 14)
    private String phoneNumber;


    private Boolean isDefaultShippingAddress;
}
