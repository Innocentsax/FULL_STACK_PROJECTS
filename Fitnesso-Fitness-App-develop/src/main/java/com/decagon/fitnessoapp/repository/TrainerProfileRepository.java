package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
    List<TrainerProfile> findAll();
    Optional<TrainerProfile> findByTrainerAndIsActive(Person trainer, Boolean isActive);
    List<TrainerProfile> findAllByIsActive(Boolean isActive);
}
