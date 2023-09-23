package com.example.food.repositories;


import com.example.food.Enum.Role;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Users> findByConfirmationToken(String token);
    List<Users> findAllByRole(Role role);
}
