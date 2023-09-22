package com.decagon.fintechpaymentapisqd11a.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallet_tbl")
public class Wallet extends BaseClass {

    private Double balance = 0.00;

    @Column(length = 10, nullable = false)
    @Size(min = 10, max = 10)
    private String acctNumber;

    @Column(nullable = false)
    private String bankName;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionList;

}
