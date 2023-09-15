package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order extends Base{

    @OneToMany (mappedBy = "order")
    @ToString.Exclude
    private List<OrderItem> customerOrder = new ArrayList<>();

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime timeOfCompletion;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;

    private Double deliveryFee;
    private Double discount;
    private Double price;

    @OneToOne
    private Address address;

    @ManyToOne
    @JsonIgnore
    private User customer;


}

