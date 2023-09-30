package com.decagon.dev.paybuddy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    CREDIT("credit"), DEBIT("debit"), FUNDWALLET("fundwallet"), WITHDRAWAL("withdrawal"), MAKEPAYMENT("makePayment");
    private final String transaction ;

}
