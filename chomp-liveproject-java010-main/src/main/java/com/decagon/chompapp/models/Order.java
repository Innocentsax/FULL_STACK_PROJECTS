package com.decagon.chompapp.models;

import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "orders")
@AllArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@Builder
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateOrdered;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateDelivered;

    private Double totalPrice;

    private Double flatRate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    @OneToOne
    private ShippingAddress shippingAddress;


    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

}
