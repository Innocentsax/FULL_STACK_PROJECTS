package com.decagon.safariwebstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseModel{


    private String address;

    private String state;

    private String city;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_shipping_address")
    private Boolean isDefaultShippingAddress;

}
