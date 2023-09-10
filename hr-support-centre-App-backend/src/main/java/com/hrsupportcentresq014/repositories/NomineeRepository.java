package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Award;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Nominee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NomineeRepository extends MongoRepository<Nominee, String> {
    Optional<Nominee> findByNomineeAndAward(Employee nominated, Award award);

}
