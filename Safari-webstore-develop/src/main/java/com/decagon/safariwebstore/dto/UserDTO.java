package com.decagon.safariwebstore.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDTO {
    private String firstName;

    private String lastName;

    private  String email;

    private String gender;
}
