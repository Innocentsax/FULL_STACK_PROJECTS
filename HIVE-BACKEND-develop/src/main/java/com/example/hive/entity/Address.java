package com.example.hive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID address_id;
    private Integer number;
    private String street;
    private String city;
    private String state;
    private String country;


}
