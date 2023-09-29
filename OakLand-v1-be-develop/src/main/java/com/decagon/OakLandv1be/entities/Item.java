package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item_tbl")
public class Item extends BaseEntity{

    private String productName;
    private String imageUrl;

    private Integer orderQty;
    private Double unitPrice;
    private Double subTotal;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
