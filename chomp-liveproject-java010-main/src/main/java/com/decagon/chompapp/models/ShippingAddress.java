package com.decagon.chompapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;


    @Size(min = 2, max=64, message = "lastname must be at least two letter long")
    @Column(nullable = false)
    private String recipientName;


    private String streetAddress;

    @Email
    private String email;

    private String state;

    private String city;

    //    TODO phone number format
    @Size(max = 14)
    private String phoneNumber;


    private Boolean isDefaultShippingAddress;

//    @ManyToOne
//    private User user;
}
