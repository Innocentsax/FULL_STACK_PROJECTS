package com.decagon.chompapp.enums;

public enum TransactionType {
    FUNDWALLET("fundwallet"), WITHDRAWAL("withdrawal"),MAKEPAYMENT("makePayment");
   private final String transaction ;

    TransactionType(String transaction) {
        this.transaction = transaction;
    }
}
