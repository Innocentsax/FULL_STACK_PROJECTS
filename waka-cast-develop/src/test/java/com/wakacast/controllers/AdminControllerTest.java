package com.wakacast.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.*;
import com.wakacast.dto.DailyEmailDTO;
import com.wakacast.dto.GenreDto;
import com.wakacast.dto.LanguageDto;
import com.wakacast.dto.RoleDto;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.CastCallReportPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.models.CastCall;
import com.wakacast.services.AdminService;
import com.wakacast.services.CastCallService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CastCallService castCallService;
    @MockBean
    private AdminService adminService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getAllUsers() throws Exception {
        UserPage userPage = new UserPage();
        mockMvc.perform(get("/all-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPage)))
                .andExpect(status().isOk());
    }

    @Test
    void addNewGenre() throws Exception {
        GenreDto genreDto = new GenreDto();
        mockMvc.perform(post("/add-genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCastCallAdmin() throws Exception {
        mockMvc.perform(delete("/delete-cast-call-admin/2")).andExpect(status().isOk());
    }

    @Test
    void getAllCastCallAdmin() throws Exception {
        CastCallPage castCallPage = new CastCallPage();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/get-all-cast-calls-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(castCallPage)))
                .andExpect(status().isOk());
    }


    @Test
    void sendEmail() throws Exception {
        DailyEmailDTO emailDTO = new DailyEmailDTO();
        mockMvc.perform(post("/api/admin/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void givenANewLanguage_addNewLanguageEndpoint_shouldAddNewLanguage() throws Exception {
        LanguageDto languageDto = new LanguageDto();
        mockMvc.perform(post("/api/admin/add-language")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(languageDto)))
                .andExpect(status().isOk());

    }

    @Test
    void addReasonForReportingCAstCall() throws Exception {
        ReasonToSaveDto reasonToSaveDto = new ReasonToSaveDto();
        mockMvc.perform(put("/add-reason-for-reporting-cast-call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reasonToSaveDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCastCallReported() throws Exception {
        CastCallReportPage castCallReportPage = new CastCallReportPage();
        mockMvc.perform(get("/get-all-cast-calls-reported")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(castCallReportPage)))
                .andExpect(status().isOk());
    }

    @Test
    void flagReportedCastCallAdmin() throws Exception {
        CastCall reportedCastCall = new CastCall();
        reportedCastCall.setId(2L);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/flag-cast-call-reported/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(reportedCastCall.getId())))
                .andExpect(status().isOk());
    }

    @Test
    void unFlagReportedCastCallAdmin() throws Exception {
        CastCall reportedCastCall = new CastCall();
        reportedCastCall.setId(3L);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/un-flag-cast-call-reported/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(reportedCastCall.getId())))
                .andExpect(status().isOk());
    }


    @Test
    void addNewRole() throws Exception {
        RoleDto roleDto = new RoleDto();
        mockMvc.perform(post("/add-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDto)))
                .andExpect(status().isOk());
    }


    @Test
    void activateDeactivateUser() throws Exception {
        mockMvc.perform(post("/activate-deactivate-user/1"))
                .andExpect(status().isOk());
    }


    @Test
    void addEquipmentType() throws Exception {
        EquipmentTypeDto equipmentTypeDto= new EquipmentTypeDto();
        mockMvc.perform(post("/add-equipment-type-to-db")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipmentTypeDto)))
                .andExpect(status().isOk());
    }
}
