package com.fintech.app.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_transfers")
public class LocalTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transferId;

    String senderAccountName;
    String senderAccountNumber;
    String recipientAccountName;
    String recipientAccountNumber;
    Double transferAmount;
    String narration;
    String status;
    LocalDateTime transferDate;
}
