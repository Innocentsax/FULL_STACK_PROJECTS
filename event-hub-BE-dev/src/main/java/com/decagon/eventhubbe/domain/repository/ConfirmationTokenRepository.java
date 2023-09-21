package com.decagon.eventhubbe.domain.repository;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    ConfirmationToken findByAppUser(AppUser appUser);
    Optional<ConfirmationToken> findConfirmationTokenByAppUser(AppUser appUser);

    boolean existsByAppUser(AppUser appUser);

}