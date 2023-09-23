package com.example.food.model;

import com.example.food.Enum.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@Table(name = "payment_info", indexes = {@Index(name = "idx_paymentinfo", columnList = "payment_reference")},
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"payment_reference"})
//        })
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PaymentMethod paymentMethod;
    private String paymentReference; //from third party provider
    @CreationTimestamp
    private Date createdAt;
    private BigDecimal amount;
    @OneToOne
    private Order order;
}
//
