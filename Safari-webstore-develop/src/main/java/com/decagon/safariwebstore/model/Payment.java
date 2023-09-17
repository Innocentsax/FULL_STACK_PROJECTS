package com.decagon.safariwebstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment extends BaseModel {

    @Column(name = "payment_ref")
    private String paymentReference;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "confirm_payment")
    private boolean confirmPayment;
}