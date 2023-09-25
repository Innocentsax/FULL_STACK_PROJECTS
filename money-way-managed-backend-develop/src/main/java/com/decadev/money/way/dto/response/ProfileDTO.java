package com.decadev.money.way.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String accountNumber;

    private String accountBalance;

    private String amountSpent;

    private String bank;

    private String phoneNumber;

}
