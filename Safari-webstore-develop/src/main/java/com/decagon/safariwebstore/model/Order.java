package com.decagon.safariwebstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")

public class Order extends BaseModel {
    private String quantity;

    private String deliveryMethod;

    private Date dateOrdered;

    private Double cardDiscount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelivered;

    @Column(columnDefinition = "DECIMAL")
    private Double deliveryFee;

    private String paymentType;

    private String status;

    private Double costOfProducts; //alias subtotal

    private Boolean isGift;

    @Column(columnDefinition = "DECIMAL")
    private Double totalCost;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "shippingaddress_id")
    private ShippingAddress shippingAddress;

    @OneToMany(targetEntity = OrderedItem.class)
    private List<OrderedItem> orderedItems;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

}
