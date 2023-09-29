package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address_tbl")
public class Address extends BaseEntity{

    private String fullName;
    private String phone;
    private String emailAddress;
    private String street;
    private String state;
    private String country;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Order order;
}
