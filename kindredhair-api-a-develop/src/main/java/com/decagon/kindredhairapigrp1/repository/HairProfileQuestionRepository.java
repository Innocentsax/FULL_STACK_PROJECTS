package com.decagon.kindredhairapigrp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.decagon.kindredhairapigrp1.models.HairProfileQuestion;

@Repository
public interface HairProfileQuestionRepository extends JpaRepository<HairProfileQuestion, Long>{
}
