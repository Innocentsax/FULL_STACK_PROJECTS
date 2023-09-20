package com.easyLend.userservice.domain.repository;

import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {

    JwtToken findByAccessToken(String accessToken);
    Optional<JwtToken> findJwtTokenByAccessToken(String accessToken);

    JwtToken findByRefreshToken(String refreshToken);
    Optional<JwtToken> findJwtTokenByRefreshToken(String refreshToken);
    List<JwtToken> findAllByUser(AppUser user);
    JwtToken findByUser(AppUser user);
}
