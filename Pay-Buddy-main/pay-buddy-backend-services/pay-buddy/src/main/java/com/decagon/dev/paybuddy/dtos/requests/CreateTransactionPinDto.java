package com.decagon.dev.paybuddy.dtos.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateTransactionPinDto {
    @NotBlank
    @NonNull
    private String oldPin;
    private String newPin;
    private String confirmNewPin;


}
