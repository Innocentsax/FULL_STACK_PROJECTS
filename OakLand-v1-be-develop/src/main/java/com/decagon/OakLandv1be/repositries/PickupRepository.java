package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.PickupCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<PickupCenter, Long> {
//    Optional<PickupCenter> findByName(String name);

    Optional<PickupCenter> findByEmail(String email);

    boolean existsByName(String name);
}