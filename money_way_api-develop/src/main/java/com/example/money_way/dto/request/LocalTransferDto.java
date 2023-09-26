package com.example.money_way.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalTransferDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String referenceId;

    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @NotBlank(message = "Pin cannot be blank")
    private String pin;

    private boolean saveBeneficiary;

}
