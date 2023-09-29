package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "customer_tbl")
public class Customer extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart = new Cart();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

//    @JsonIgnore
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Item> items;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Product> favorites = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer",  fetch = FetchType.EAGER)
    private Set<Address> addressBook;
    private boolean isActive = true;


}
