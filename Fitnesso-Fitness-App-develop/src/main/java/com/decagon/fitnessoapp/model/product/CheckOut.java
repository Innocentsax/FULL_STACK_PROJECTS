package com.decagon.fitnessoapp.model.product;

import com.decagon.fitnessoapp.model.user.Address;
import com.decagon.fitnessoapp.model.user.PaymentCard;
import com.decagon.fitnessoapp.model.user.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "check_out")
public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    private String shoppingCartUniqueId;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @OneToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id", nullable = false)
    private Address shippingAddress;

    @NotNull
    @OneToOne
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id", nullable = false)
    private Address billingAddress;

    @NotNull
    @OneToOne
    @JoinColumn(name = "payment_card", referencedColumnName = "id", nullable = false)
    private PaymentCard paymentCard;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp orderDate;

    private String couponCode;

    private String referenceNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SHIPPING_METHOD shippingMethod;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TRANSACTION_STATUS transactionStatus;

}
