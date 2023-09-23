package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.product.CheckOut;
import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
import com.decagon.fitnessoapp.model.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
    Optional<CheckOut> findByReferenceNumber(String referenceNumber);
//    Optional<CheckOut> findCheckOutByShoppingCartId(Long cartId);
    Optional<CheckOut> findCheckOutByShoppingCartUniqueId(String shoppingCartId);

}
