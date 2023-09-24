package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order extends BaseModel {

    @OneToOne
    private Product product;
    @NotBlank(message = "Quantity field cannot be empty")
    private Integer quantity;
    @OneToOne
    private  ShippingAddress shippingAddress;
    @ManyToOne
    private Payment payment;
    @NotBlank(message = "Status cannot be empty")
    private  String status;

}
