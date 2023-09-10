package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token,String>{

    List<Token> findTokenByEmployeeAndExpiredIsFalseAndRevokedIsFalse(Employee employee);
    Optional<Token> findByJwtToken(String token);
}