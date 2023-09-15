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
public class CartItem extends Base{


    @ManyToOne
    private Product product;
    private int unit;

    @JsonIgnore
    @ManyToOne
    private Cart cart;
}
