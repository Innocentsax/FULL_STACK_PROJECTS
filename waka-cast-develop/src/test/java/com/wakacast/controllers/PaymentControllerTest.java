package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.SubscriptionDTO;
import com.wakacast.services.PaymentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp() {

    }

    @Test
    void initializeTransaction() throws Exception {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/payment/subscribe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(subscriptionDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void confirmPayment() throws Exception {
        when(paymentService.confirmPayment("token", "dfggkj12")).thenReturn("Successful");
        mvc.perform(get("/api/payment/confirm/token")
                .param("reference", "dfggkj12")
                .param("trxref", "dfggkj12"))
                .andExpect(status().isOk());
    }
}