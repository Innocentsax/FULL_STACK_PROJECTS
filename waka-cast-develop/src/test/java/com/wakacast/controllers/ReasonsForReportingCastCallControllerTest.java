//package com.wakacast.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wakacast.configurations.securityConfigs.JwtAuthenticationEntryPoint;
//import com.wakacast.configurations.securityConfigs.JwtTokenUtil;
//import com.wakacast.dto.pagesCriteria.CastCallPage;
//import com.wakacast.dto.pagesCriteria.CastCallReportPage;
//import com.wakacast.models.ReasonForReportingCastCall;
//import com.wakacast.services.AdminService;
//import com.wakacast.services.CastCallService;
//import com.wakacast.services.serviceImpl.JwtUserDetailsService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class ReasonsForReportingCastCallControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AdminService adminService;
//    @MockBean
//    private JwtTokenUtil jwtTokenUtil;
//    @MockBean
//    private JwtUserDetailsService userDetailsService;
//    @MockBean
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Test
//    void getReasonsForReporting() throws Exception {
//        List<ReasonForReportingCastCall> reason = new ArrayList<>();
//        ObjectMapper objectMapper = new ObjectMapper();
//        mockMvc.perform(get("/get-active-cast-calls")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}