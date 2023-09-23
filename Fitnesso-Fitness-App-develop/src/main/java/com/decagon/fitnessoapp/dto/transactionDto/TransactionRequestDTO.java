package com.decagon.fitnessoapp.dto.transactionDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class TransactionRequestDTO {
    private String amount;
    private String email;
    private String reference;
    private String callback_url;
    private Map<?, ?> metadata;
    private List<String> channels;
    private Integer transaction_charge;
}
