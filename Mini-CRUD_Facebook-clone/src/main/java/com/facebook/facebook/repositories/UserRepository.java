package com.facebook.facebook.repositories;

import com.facebook.facebook.entities.FacebookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FacebookUser, Long> {
    boolean existsByEmail(String email);
    Optional<FacebookUser> findByEmail(String email);


    FacebookUser findByName(String name);
//    Optional<FacebookUser> findByUsername(String username);

}
