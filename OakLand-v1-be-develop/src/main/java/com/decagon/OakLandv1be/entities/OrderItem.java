package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orderItem_tbl")
public class OrderItem extends BaseEntity{

    private String productName;
    private String imageUrl;

    private Integer orderQty;
    private Double unitPrice;
    private Double subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
