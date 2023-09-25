package com.fintech.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationAccountNumber;
    private String destinationBank;
    private String destinationFullName;
    private Double amount;
    private String narration;
    private String status;
    private String clientRef;

    private Long flwRef;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private User user;

    private String type;
    private String senderFullName;
    private String senderBankName;
    private String senderAccountNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifyAt;

}
