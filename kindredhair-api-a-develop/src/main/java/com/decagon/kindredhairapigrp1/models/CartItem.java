package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart_items")
public class CartItem extends BaseModel {

    @NotBlank(message = "quantity cannot be empty")
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    private  Product product;
    @ManyToOne
    private UserDetails userDetails;

}
