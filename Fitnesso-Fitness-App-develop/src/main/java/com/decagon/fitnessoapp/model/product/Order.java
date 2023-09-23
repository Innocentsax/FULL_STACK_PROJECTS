package com.decagon.fitnessoapp.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_status")
    @Enumerated(EnumType.STRING)
    private ORDER_STATUS orderStatus;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_out_id", referencedColumnName = "id", nullable = false)
    private CheckOut checkOut;


}