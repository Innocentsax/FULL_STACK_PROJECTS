package com.decagon.kindredhairapigrp1.repository;

import com.decagon.kindredhairapigrp1.models.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
