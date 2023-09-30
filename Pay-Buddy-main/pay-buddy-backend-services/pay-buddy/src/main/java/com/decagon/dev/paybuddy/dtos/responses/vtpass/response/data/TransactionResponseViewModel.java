package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransactionResponseViewModel {
    private int count;
    private int totalPage;
    private List<TransactionResponse> list;
}
