package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank(message = "Email field  cannot be empty") @Email String email);
}