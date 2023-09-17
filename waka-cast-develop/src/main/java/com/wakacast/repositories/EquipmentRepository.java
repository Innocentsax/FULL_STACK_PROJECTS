package com.wakacast.repositories;

import com.wakacast.models.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findEquipmentByIdAndEquipmentOwnerEmail(long id, String email);
    Page<Equipment> findAll(Pageable pageable);
    Equipment findEquipmentById(Long id);
}
