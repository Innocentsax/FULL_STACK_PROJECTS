package com.wakacast.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.services.EquipmentSearchService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EquipmentSearchController.class)
class EquipmentSearchControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EquipmentSearchCriteria equipmentSearchCriteria;
    @MockBean
    private EquipmentSearchService equipmentSearchService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private EquipmentSearchController equipmentSearchController;


    private EquipmentPage equipmentPage;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        equipmentPage = new EquipmentPage();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getEquipmentsByFilter() throws Exception {
        EquipmentSearchCriteria equipmentSearchCriteria = new EquipmentSearchCriteria();
        mvc.perform(MockMvcRequestBuilders.get("/search-equipment-filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(equipmentPage))
                        .content(objectMapper.writeValueAsString(equipmentSearchCriteria)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEquipments() throws Exception {
        EquipmentPage equipmentPage = new EquipmentPage();
        mvc.perform(get("/get-all-equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipmentPage)))
                .andExpect(status().isOk());
    }

    @Test
    void getEquipmentById() throws Exception {
        mvc.perform(get("/get-equipment-by-id/3"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEquipmentTypes() throws Exception {
        mvc.perform(get("/get-all-equipment-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}