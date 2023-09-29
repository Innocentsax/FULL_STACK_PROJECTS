package com.decagon.OakLandv1be.dto;


import com.decagon.OakLandv1be.entities.Data;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionInitResponseDto {

    private Boolean status;
    private String message;
    private Data data;
}
