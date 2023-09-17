package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.services.TalentSearchService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TalentSearchController.class)
class TalentSearchControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TalentSearchCriteria talentSearchCriteria;
    @MockBean
    private TalentSearchService talentSearchService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private ModelMapper mapper;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private TalentPage talentPage;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        talentPage = new TalentPage();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getTalentsByFilter() throws Exception {
        TalentSearchCriteria talentSearchCriteria = new TalentSearchCriteria();
        mvc.perform(MockMvcRequestBuilders.get("/search-talent-filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(talentPage))
                        .content(objectMapper.writeValueAsString(talentSearchCriteria)))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getAllTalents() {
        mvc.perform(MockMvcRequestBuilders.get("/get-all-talents")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(talentPage)))
                .andExpect(status().isOk());
    }
}