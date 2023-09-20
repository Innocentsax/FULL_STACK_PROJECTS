package com.easyLend.userservice.domain.repository;

import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.entity.VerificationEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationEmailRepository extends JpaRepository<VerificationEmail,Long> {
    VerificationEmail findByUser(AppUser appUser);
    VerificationEmail findByToken(String token);

}
