package com.wakacast.controllers;

import com.wakacast.dto.EquipmentRequestedDto;
import com.wakacast.dto.EquipmentResponseDto;
import com.wakacast.dto.EquipmentTypeResponseDto;
import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.models.Equipment;
import com.wakacast.models.EquipmentType;
import com.wakacast.services.AdminService;
import com.wakacast.services.EquipmentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EquipmentSearchController {

    private final EquipmentSearchService equipmentSearchService;
    private final EquipmentSearchService equipmentService;
    private final AdminService adminService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search-equipment-filter")
    public ResponseEntity<Page<Equipment>> getTalentsByFilter(EquipmentSearchCriteria equipmentSearchCriteria) {
        return new ResponseEntity<>(equipmentSearchService.getEquipmentWithFilter(equipmentSearchCriteria),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-equipment-by-id/{id}")
    public ResponseEntity<EquipmentResponseDto> getEquipmentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(equipmentService.getEquipmentById(id),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-all-equipments")
    public ResponseEntity<Page<EquipmentResponseDto>> getAllEquipments(EquipmentPage equipmentPage) {
        return new ResponseEntity<>(equipmentService.getAllEquipment(equipmentPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-equipment-types")
    public ResponseEntity<List<EquipmentTypeResponseDto>> getAllEquipmentTypes() {
        return new ResponseEntity<>(equipmentService.getAllEquipmentTypes(), HttpStatus.OK);
    }
}


