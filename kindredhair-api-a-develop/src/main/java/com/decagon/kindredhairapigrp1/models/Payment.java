package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payments")
public class Payment extends BaseModel {

    private Integer shippingCost;
    @NotBlank(message = "Tax amount cannot be empty")
    private Integer tax;
    @NotBlank(message = "TotalAMount cost cannot be empty")
    private Integer totalAmount;
    @ManyToOne
    private  UserDetails userDetails;
    @OneToMany
    private Set<Order> orders;

}
