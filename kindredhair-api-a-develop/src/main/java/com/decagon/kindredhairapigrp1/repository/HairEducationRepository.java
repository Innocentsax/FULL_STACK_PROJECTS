package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.UserHairEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HairEducationRepository extends JpaRepository<UserHairEducation, Long> {
    List<UserHairEducation> findAllByUserDetails_Id(Long userID);
}
