package com.decagon.fitnessoapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressRegReq {
    @NotNull
    private String userName;

    @NotNull
    private String streetDetail;

    @NotNull
    private String state;

    @NotNull
    private String city;

    private String zipCode;

    @NotNull
    private String country;
}
