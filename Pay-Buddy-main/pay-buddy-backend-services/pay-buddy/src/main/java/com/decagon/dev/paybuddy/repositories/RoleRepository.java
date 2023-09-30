package com.decagon.dev.paybuddy.repositories;

import com.decagon.dev.paybuddy.models.Role;
import com.decagon.dev.paybuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
