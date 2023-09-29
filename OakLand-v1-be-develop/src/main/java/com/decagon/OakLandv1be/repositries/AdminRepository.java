package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
