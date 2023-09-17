package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.services.RoleSearchService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import lombok.SneakyThrows;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoleSearchController.class)
class RoleSearchControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private RoleSearchService roleSearchService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private RoleMatchSearchCriteria roleMatchSearchCriteria;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private CastCallPage castCallPage;
    private ObjectMapper objectMapper;
    private TalentPage userPage;



    @BeforeEach
    void setUp() {
        castCallPage = new CastCallPage();
        objectMapper = new ObjectMapper();
    }


    @Test
    void getAllMatchingRoles() throws Exception {
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();
        mvc.perform(MockMvcRequestBuilders.get("/search-matching-roles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(castCallPage))
                        .content(objectMapper.writeValueAsString(roleMatchSearchCriteria)))
                .andExpect(status().isOk());
    }

    @Test
    void getSuggestedCastCalls() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/suggested-cast-calls"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getUsersByRole() {
        UserRoleSearchDTO userRoleSearchDTO = new UserRoleSearchDTO();
        mvc.perform(MockMvcRequestBuilders.get("/get-users-by-roles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userPage))
                        .content(objectMapper.writeValueAsString(userRoleSearchDTO)))
                .andExpect(status().isOk());
    }
}