package com.example.hive.repository;

import com.example.hive.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByNumberAndStreetAndCityAndStateAndCountry(Integer number, String street, String city, String state, String country);
}

