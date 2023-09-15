package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<BlacklistToken, Long> {
    boolean existsByToken(String token);
}
