package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String email);

}
