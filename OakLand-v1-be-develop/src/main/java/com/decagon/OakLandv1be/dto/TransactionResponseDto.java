package com.decagon.OakLandv1be.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponseDto {

    private Long id;
    private String date;
    private String time;
    private String amount;
    private String purpose;
    private String reference;
    private String status;

}
