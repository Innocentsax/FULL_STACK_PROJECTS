package com.wakacast.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.pages_criteria.CastCallPage;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(CastCallController.class)
class CastCallControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CastCallService  castCallService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Test
    void postCastCall() throws Exception {
        CastCallDto castCallDto = new CastCallDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/post-cast-call")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(castCallDto)))
                .andExpect(status().isOk());
    }

    @Test
    void editCastCall() throws Exception {
        CastCallDto castCallDto = new CastCallDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/edit-cast-call/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(castCallDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCastCall() throws Exception {
        mockMvc.perform(delete("/delete-cast-call/1")).andExpect(status().isOk());
    }

    @Test
    void getAllActiveCastCalls() throws Exception {
        CastCallPage castCallPage = new CastCallPage();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/get-active-cast-calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(castCallPage)))
                .andExpect(status().isOk());
    }

    @Test
    void applyForCastCall() throws Exception {
        mockMvc.perform(put("/apply-for-cast-call/1"))
                .andExpect(status().isOk());
    }

    @Test
    void viewAllCastCallsByAProducer() throws Exception {
        CastCallPage castCallPage = new CastCallPage();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/view-cast-calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(castCallPage)))
                .andExpect(status().isOk());
    }

    @Test
    void GetAllCastCallsApplicants() throws Exception {
        CastCallPage castCallPage = new CastCallPage();
        ObjectMapper objectMapper = new ObjectMapper();
        long castCallId = 1L;
        mockMvc.perform(get("/get-cast-call-applicant/"+castCallId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(castCallPage)))
                .andExpect(status().isOk());
    }
}