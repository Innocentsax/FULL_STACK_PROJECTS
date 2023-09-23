package com.decagon.fitnessoapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdatePersonResponse {

    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String phoneNumber;
}
