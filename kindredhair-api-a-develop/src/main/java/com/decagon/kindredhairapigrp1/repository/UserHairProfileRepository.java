package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.UserHairProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHairProfileRepository extends JpaRepository<UserHairProfile, Long> {
}
