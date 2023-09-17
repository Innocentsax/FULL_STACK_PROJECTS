package com.wakacast.repositories;

import com.wakacast.models.SearchAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchAgentRepository extends JpaRepository<SearchAgent, Long> {
    Optional<SearchAgent> findSearchAgentByIdAndUserEmail(long searchAgentId, String email);
}
