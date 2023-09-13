package com.decagon.adire.repository;

import com.decagon.adire.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    Page<Customer> findByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String emailSearchTerm, String firstNameSearchTerm, String lastNameSearchTerm, Pageable pageable);
}
