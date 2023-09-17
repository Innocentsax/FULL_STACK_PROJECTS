package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.Address;
import com.decagon.safariwebstore.model.ShippingAddress;
import com.decagon.safariwebstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    Optional<List<ShippingAddress>> findAllByUser(User user);
    Boolean existsAddressByUserAndAddressAndCityAndState(User user, String address, String city, String state);
    Optional<ShippingAddress> findAddressByUserAndAddressAndCityAndState(User user, String address, String city, String state);
}
