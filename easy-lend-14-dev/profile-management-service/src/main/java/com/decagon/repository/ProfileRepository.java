package com.decagon.repository;

import com.decagon.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
//    @Query(value = "SELECT * FROM profile WHERE user_id =?", nativeQuery = true)
//    Optional<Profile> findBy_user_id(String userId);
    Optional<Profile> findByUserId(String userId);

}