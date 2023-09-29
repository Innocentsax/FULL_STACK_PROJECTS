package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomer(Customer customer);
}
