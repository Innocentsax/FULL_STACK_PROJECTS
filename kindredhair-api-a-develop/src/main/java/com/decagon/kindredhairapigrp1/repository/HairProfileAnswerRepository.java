package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.HairProfileAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HairProfileAnswerRepository extends JpaRepository<HairProfileAnswer, Long> {
    
}
