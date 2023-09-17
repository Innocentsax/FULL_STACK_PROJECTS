package com.decagon.safariwebstore.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cart_items")
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseModel{

    private Long productId;

    private String quantity;

    @Column(columnDefinition = "decimal")
    private Double price;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String size;

    private String color;

    private String productImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
