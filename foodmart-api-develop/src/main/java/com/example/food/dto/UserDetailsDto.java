package com.example.food.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserDetailsDto {
    private String firstName;
    private String lastName;
    private String email;
    private String baseCurrency;
    private Date dateOfBirth;
    private Date createdAt;
}
