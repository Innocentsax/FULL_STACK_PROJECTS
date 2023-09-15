package com.example.cedarxpressliveprojectjava010.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderItem extends Base{


    @ManyToOne
    private Product product;
    private int unit;

    private Double price;

    @ManyToOne
    @JsonIgnore
    private Order order;


}
