package com.example.food.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String streetAddress;
    private String city;
    private String state;
    private Boolean isDefaultAddress;
    private Date createdAt;
    private Date modifiedAt;
    @OneToOne
    //Todo @JoinColumn
    private Order order;

}
