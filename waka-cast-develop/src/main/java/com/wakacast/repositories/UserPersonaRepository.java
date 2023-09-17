package com.wakacast.repositories;

import com.wakacast.models.UserPersona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPersonaRepository extends JpaRepository<UserPersona, Long> {
    Optional<UserPersona> findUserPersonaByPersona(String persona);
}
