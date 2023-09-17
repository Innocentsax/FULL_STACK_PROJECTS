package com.wakacast.services;

import com.wakacast.dto.EquipmentRequestedDto;
import com.wakacast.dto.EquipmentResponseDto;
import com.wakacast.dto.EquipmentTypeDto;
import com.wakacast.dto.EquipmentTypeResponseDto;
import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.models.Equipment;
import com.wakacast.models.EquipmentType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EquipmentSearchService {
    Page<Equipment> getEquipmentWithFilter(EquipmentSearchCriteria equipmentSearchCriteria);
    Page<EquipmentResponseDto> getAllEquipment(EquipmentPage equipmentPage);
    EquipmentResponseDto getEquipmentById(Long id);
    List<EquipmentTypeResponseDto> getAllEquipmentTypes();
    }
