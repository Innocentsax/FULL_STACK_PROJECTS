package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findCartItemByCartAndProduct (Cart cart, Product product);

    @Modifying
    @Query("delete from CartItem s where s.cart.Id = :cartId and s.product.Id = :productId")
    void deleteCartItemByCartAndProduct(Long cartId, Long productId);

    @Query("select s from  CartItem s where s.cart.Id = :cart and s.product.Id = :productId")
    Optional<CartItem> findCartItemByCartAndProductId(Cart cart, Long productId);
}
