package com.goCash.entities;

import com.goCash.enums.PurchaseType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "airtime_data_purchases")

public class AirtimeData extends BaseEntity{


    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "purchase_type", nullable = false)
    private PurchaseType purchaseType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
