package com.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Product product;
    @Column(name = "quantity")
    private @NotNull int quantity;
    @Column(name = "subTotal")
    private @NotNull BigDecimal subTotal;
    @JsonIgnore
    @ManyToOne(targetEntity = Cart.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_Id", referencedColumnName = "id",
            foreignKey = @ForeignKey
                    (name = "cart_carttem_fk"))
    private Cart cart;


}
