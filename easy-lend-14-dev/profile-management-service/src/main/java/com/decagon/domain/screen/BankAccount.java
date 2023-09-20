package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.BankAccountDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccount {
    private String bank;
    private String accountNumber;
    private String accountName;

    public BankAccount(BankAccountDTO bankAccountDTO) {
        this.bank = bankAccountDTO.getBank();
        this.accountNumber = bankAccountDTO.getAccountNumber();
        this.accountName = bankAccountDTO.getAccountName();
    }
}