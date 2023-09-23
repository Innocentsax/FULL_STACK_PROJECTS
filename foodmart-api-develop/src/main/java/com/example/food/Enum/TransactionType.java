package com.example.food.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    FUNDWALLET("fundwallet"), WITHDRAWAL("withdrawal"),MAKEPAYMENT("makePayment");
   private final String transaction ;
}

