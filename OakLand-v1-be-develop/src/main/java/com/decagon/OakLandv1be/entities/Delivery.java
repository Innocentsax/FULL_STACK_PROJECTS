//package com.decagon.OakLandv1be.entities;
//
//
//import com.decagon.OakLandv1be.enums.DeliveryStatus;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "delivery_tbl")
//public class Delivery extends BaseEntity{
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Order> orders;
//
//    @Enumerated(EnumType.STRING)
//    private DeliveryStatus status;
//}
