package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;


import com.decagon.dev.paybuddy.enums.TransactionStatus;
import com.decagon.dev.paybuddy.enums.TransactionType;
import com.decagon.dev.paybuddy.models.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransactionResponse {
    private Long transactionId;
    private String referenceNumber;
    private String name;
    private String bankCode;
    private BigDecimal amount;
    private String transactionReference;
    private TransactionType transactionType;
    private LocalDate dateOfTransaction;
    private TransactionStatus transactionStatus;
    private String description;


    public static TransactionResponse mapFromTransaction (Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .referenceNumber(transaction.getReferenceNumber())
                .name(transaction.getName())
                .bankCode(transaction.getBankCode())
                .amount(transaction.getAmount())
                .transactionReference(transaction.getTransactionReference())
                .transactionType(transaction.getTransactionType())
                .dateOfTransaction(transaction.getDateOfTransaction())
                .transactionStatus(transaction.getTransactionStatus())
                .description(transaction.getDescription())
                .build();
    }

    public static List<TransactionResponse> mapFromTransaction (List<Transaction> transactions){
        return  transactions.stream()
                .map(TransactionResponse::mapFromTransaction)
                .collect(Collectors.toList());
    }

}
