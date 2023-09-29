package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface StateRepository extends JpaRepository<State, Long> {


    boolean existsByName(String nameOfState);

    Optional<State> findByName(String stateName);
}
