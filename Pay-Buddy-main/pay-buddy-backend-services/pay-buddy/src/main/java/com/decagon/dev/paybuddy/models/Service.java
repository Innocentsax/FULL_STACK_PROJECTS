package com.decagon.dev.paybuddy.models;

import javax.persistence.*;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,unique = true)
    private Long serviceId;
    private String serviceName;
    private String trackingNumber;
    private Boolean isAvailable;
}
