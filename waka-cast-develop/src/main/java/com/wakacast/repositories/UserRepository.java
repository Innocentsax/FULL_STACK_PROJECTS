package com.wakacast.repositories;

import com.wakacast.enums.UserType;
import com.wakacast.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByLoginToken(String loginToken);
    List<User> findUserByUserType(UserType userType);
    @Query("SELECT u FROM User u JOIN u.userPersonas ur WHERE ur.persona = 'TALENT'")
    Page<User> findTalentAll(Pageable pageable);
}
