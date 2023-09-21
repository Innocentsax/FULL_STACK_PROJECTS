package com.decagon.eventhubbe.domain.repository;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken,String> {
    JwtToken findByAccessToken(String accessToken);
    Optional<JwtToken> findJwtTokenByAccessToken(String accessToken);

    JwtToken findByRefreshToken(String refreshToken);
    Optional<JwtToken> findJwtTokenByRefreshToken(String refreshToken);
    List<JwtToken> findAllByAppUser(AppUser appUser);

}
