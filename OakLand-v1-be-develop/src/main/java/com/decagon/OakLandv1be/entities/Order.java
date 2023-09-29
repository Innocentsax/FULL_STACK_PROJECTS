package com.decagon.OakLandv1be.entities;


import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.ModeOfDelivery;
import com.decagon.OakLandv1be.enums.PickupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "order_tbl")
public class Order extends BaseEntity{

    @OneToOne( cascade = CascadeType.ALL)
    private ModeOfPayment modeOfPayment;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> items = new HashSet<>();

    private Double deliveryFee;


    @Enumerated(EnumType.STRING)
    private ModeOfDelivery modeOfDelivery;

    private DeliveryStatus deliveryStatus;

    private Double grandTotal;

    private Double discount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Transaction transaction;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_id")
    private PickupCenter pickupCenter;

    @Enumerated(EnumType.STRING)
    private PickupStatus pickupStatus;
}
