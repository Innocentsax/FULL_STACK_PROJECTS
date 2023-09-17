package com.wakacast.services.service_impl;

import com.wakacast.dto.EquipmentResponseDto;
import com.wakacast.dto.EquipmentTypeDto;
import com.wakacast.dto.EquipmentTypeResponseDto;
import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.models.Equipment;
import com.wakacast.models.EquipmentType;
import com.wakacast.models.User;
import com.wakacast.repositories.EquipmentRepository;
import com.wakacast.repositories.EquipmentTypeRepository;
import com.wakacast.repositories.criteri_class.EquipmentSearchCriteriaRepository;
import com.wakacast.services.EquipmentSearchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EquipmentSearchServiceImpl implements EquipmentSearchService {
    private final EquipmentSearchCriteriaRepository equipmentSearchCriteriaRepository;
    private final EquipmentRepository equipmentRepository;
    private final ModelMapper mapper;
    private final EquipmentTypeRepository equipmentTypeRepository;



    @Override
    public Page<Equipment> getEquipmentWithFilter(EquipmentSearchCriteria equipmentSearchCriteria) {
        return equipmentSearchCriteriaRepository.findEquipmentWithFilter(equipmentSearchCriteria);
    }

    @Override
    @Cacheable(cacheNames = "equipments")
    public Page<EquipmentResponseDto> getAllEquipment(EquipmentPage equipmentPage) {
        Sort sort = Sort.by(equipmentPage.getSortDirection(), equipmentPage.getSortBy());
        Pageable pageable = PageRequest.of(equipmentPage.getPageNumber(), equipmentPage.getPageSize(), sort);
        Page<Equipment> equipments = equipmentRepository.findAll(pageable);
        return mapEquipmentToEquipmentCallDTO(equipments);
    }
    @Override
    public EquipmentResponseDto getEquipmentById(Long id) {
        Equipment equipmentGotten = equipmentRepository.findEquipmentById(id);
        return mapper.map(equipmentGotten, EquipmentResponseDto.class);
    }

    private Page<EquipmentResponseDto> mapEquipmentToEquipmentCallDTO(Page<Equipment> equipments) {
        List<EquipmentResponseDto> equipmentRequestedDtoList =  new ArrayList<>();
        for(Equipment equipment : equipments){
            EquipmentResponseDto equipmentResponseDto = mapper.map(equipment, EquipmentResponseDto.class);
            equipmentRequestedDtoList.add(equipmentResponseDto);
        }
        return new PageImpl<>(equipmentRequestedDtoList);
    }

    @Override
    public List<EquipmentTypeResponseDto> getAllEquipmentTypes() {
        List<EquipmentType> equipmentType = equipmentTypeRepository.findAll();
        List<EquipmentTypeResponseDto> equipmentTypeToGet = new ArrayList<>();
        for(EquipmentType equipType : equipmentType){
            EquipmentTypeResponseDto equipmentT = mapper.map(equipType, EquipmentTypeResponseDto.class);
            equipmentTypeToGet.add(equipmentT);
        }
        return equipmentTypeToGet;
    }
}


