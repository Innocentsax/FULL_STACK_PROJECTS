package com.decagon.chompapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private int quantity;

    private double cartTotal;

   @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.REMOVE)
   private List<CartItem> cartItemList;


    @JsonIgnore
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Cart(User user) {
        this.user = user;
    }


}
