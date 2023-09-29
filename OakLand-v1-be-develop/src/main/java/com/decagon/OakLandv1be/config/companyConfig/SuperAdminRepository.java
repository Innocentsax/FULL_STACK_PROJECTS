package com.decagon.OakLandv1be.config.companyConfig;

import com.decagon.OakLandv1be.entities.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
}