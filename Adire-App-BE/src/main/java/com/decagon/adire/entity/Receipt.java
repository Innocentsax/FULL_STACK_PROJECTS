package com.decagon.adire.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity{
    private String customerName;
    private String phoneNumber;
    private String address;
    private String materialType;
    private String duration;
    private double price;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private  Customer customer;
    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private  Order order;


}
