package com.decagon.chompapp.models;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @OneToOne
    private Product product;

    @Column(name = "quantity")
    private @NotNull int quantity;

    @Column(name = "price")
    private @NotNull double subTotal;

    @ManyToOne
//            ( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_Id", referencedColumnName = "cartId")
    private Cart cart;


}
