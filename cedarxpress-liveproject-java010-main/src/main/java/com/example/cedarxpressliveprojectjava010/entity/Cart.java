package com.example.cedarxpressliveprojectjava010.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Transient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Cart extends Base{


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private User customer;

    @Transient
    public double getCost(){
        double total = 0.00;
        for(CartItem e: cartItems){
            total += (e.getUnit() * e.getProduct().getPrice());
        }
        return total;
    }
}
