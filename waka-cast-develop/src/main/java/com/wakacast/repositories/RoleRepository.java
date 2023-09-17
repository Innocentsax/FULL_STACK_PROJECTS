package com.wakacast.repositories;

import com.wakacast.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleTitle(String roleTitle);
    Optional<Role> findRoleById(Long id);
}
