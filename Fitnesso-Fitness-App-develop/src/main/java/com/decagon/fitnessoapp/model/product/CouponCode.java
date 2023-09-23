package com.decagon.fitnessoapp.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CouponCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @NotNull
    @Column(name = "discount_percent", nullable = false)
    private float discountPercent;

//    @Column(name = "validity", nullable = false)
//    private boolean valid = false;
    @NotNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

}
