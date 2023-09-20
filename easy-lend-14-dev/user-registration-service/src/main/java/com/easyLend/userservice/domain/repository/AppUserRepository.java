package com.easyLend.userservice.domain.repository;

import com.easyLend.userservice.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findAppUserByEmail(String email);
    Boolean existsAppUserByEmail(String email);
}
