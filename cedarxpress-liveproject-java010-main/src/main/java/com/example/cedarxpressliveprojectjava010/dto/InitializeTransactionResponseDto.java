package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.entity.Data;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitializeTransactionResponseDto {
    private String status;
    private String message;
    private Data data;
}