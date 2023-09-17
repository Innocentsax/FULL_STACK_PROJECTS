package com.wakacast.repositories;

import com.wakacast.models.EquipmentType;
import com.wakacast.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
    Optional<EquipmentType> findEquipmentByEquipmentType(String equipmentType);
}
