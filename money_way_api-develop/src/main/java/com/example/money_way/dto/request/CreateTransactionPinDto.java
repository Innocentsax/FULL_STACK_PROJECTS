package com.example.money_way.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateTransactionPinDto {
    @Size(min = 4, max = 4)
    @NotBlank(message = "Pin cannot be blank")
    private String oldPin;
    @Size(min = 4, max = 4)
    @NotBlank(message = "Pin cannot be blank")
    private String newPin;
    @Size(min = 4, max = 4)
    @NotBlank(message = "Pin cannot be blank")
    private String confirmPin;

}
