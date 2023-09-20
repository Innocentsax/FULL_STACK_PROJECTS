package com.decagon.loanagreementservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.decagon.loanagreementservice.models.Status.NEW;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "loan_Agreement")
public class LoanAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id")
    private Long id;
    private String loanAgreementId;

    @Column(name = "loan_id")
    private String loanId;
    @Column(name = "borrower_id")
    private String borrowerId;

    @Column(name = "lender_id")
    private String lenderId;
    @Column(name = "loanAmount")
    private BigDecimal loanAmount;

    @Column(name = "interest")
    private BigDecimal interestRate;

    @Column(name = "durationInDays")
    private int durationInDays;
    @Column(name = "riskTolerance")
    private int riskTolerance;
    @Enumerated(EnumType.STRING)
    private Status status = NEW;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;



}