package com.decagon.safariwebstore.repository;

import com.decagon.safariwebstore.model.ERole;
import com.decagon.safariwebstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
