package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.enums.TransactionStatus;
import lombok.*;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDto {
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private Order order;
}
