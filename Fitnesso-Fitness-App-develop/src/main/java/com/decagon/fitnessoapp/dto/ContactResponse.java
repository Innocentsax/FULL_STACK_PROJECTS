package com.decagon.fitnessoapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ContactResponse {
    private String fullName;
    private String currentWeight;
    private String goalWeight;
    @NotEmpty
    private String email;
    private String goals;
}
