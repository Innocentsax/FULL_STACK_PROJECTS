package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team, String> {


    Optional<Team> findTeamByName(String name);
}
