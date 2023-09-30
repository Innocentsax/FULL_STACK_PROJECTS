package com.decagon.dev.paybuddy.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String country;
    private String streetName;
    private String state;
    private String lga;
    private Integer houseNumber;
    private String others;

}
