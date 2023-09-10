package com.goCash.entities;

import com.goCash.enums.BillType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill_payments")
public class BillPayment extends BaseEntity {


    @Column(name = "bill_type", nullable = false)
    private BillType billType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
