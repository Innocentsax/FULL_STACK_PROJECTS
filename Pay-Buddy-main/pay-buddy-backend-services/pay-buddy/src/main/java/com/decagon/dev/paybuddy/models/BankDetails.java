package com.decagon.dev.paybuddy.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String accountName;
    private int bankId;
    private String bankCode;
    @CreationTimestamp
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
