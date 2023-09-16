package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

}
