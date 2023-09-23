package com.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal cartTotal;
    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.REMOVE)
    //Todo Look into
    private List<CartItem> cartItemList;
    @JsonIgnore
    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Users users;
}
