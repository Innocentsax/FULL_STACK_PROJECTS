package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Address;
import com.decagon.chompapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<List<Address>> findAllByUser(User user);

    Boolean existsAddressByUserAndStreetAddressAndStateAndCity(User user, String streetAddress, String state, String city);

    Optional<Address> findAddressByUserAndStreetAddressAndStateAndCity(User user, String streetAddress, String state, String city);

}
