package com.decagon.fitnessoapp.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EmailTokenRequest {

    @Email
    private String email;
}
