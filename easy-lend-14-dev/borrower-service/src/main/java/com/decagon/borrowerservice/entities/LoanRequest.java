package com.decagon.borrowerservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_request")

public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_Id", unique = true)
    private String userId;

    @Column(name = "loanAmt", nullable = false)
    private double loanAmt;

    @Column(name = "interestRate", nullable = false)
    private double interestRate;

    @Column(name = "repaymentTerm", nullable = false)
    private int repaymentTerm;

    @Column(name = "totalRepayment", nullable = false)
    private double totalRepayment;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "loan_documents", joinColumns = @JoinColumn(name = "id"))
    private List<String> requiredDocuments = new ArrayList<>();

}