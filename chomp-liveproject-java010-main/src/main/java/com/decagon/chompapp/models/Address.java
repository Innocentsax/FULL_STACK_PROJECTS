package com.decagon.chompapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;


    @Size(min = 2, max=64, message = "lastname must be at least two letter long")
    @Column(nullable = false)
    private String recipientName;


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

    @ManyToOne
    private User user;

}

