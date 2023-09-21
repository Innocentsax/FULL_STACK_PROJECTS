package com.decagon.eventhubbe.domain.repository;


import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.dto.request.RegistrationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByEmail(String email);
    Boolean existsByEmail(String email);
//    RegistrationRequest saveAppUser(RegistrationRequest appUserRequest);
}
