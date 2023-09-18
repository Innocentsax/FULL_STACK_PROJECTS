package com.example.decapay.controllers;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.services.LineItemServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LineItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class LineItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;
    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    LineItemServices lineItemServices;
    @MockBean
    LineItemResponseDto lineItemResponseDto;




    @Test
    void createLineItem() throws Exception {


        LineItemRequestDto lineItemRequestDto = new LineItemRequestDto();
        String lineItemJson = mapper.writeValueAsString(lineItemRequestDto);

        mockMvc.perform(post("/api/v1/user/line-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lineItemJson))
                .andExpect(status().isCreated());

    }
    @Test
    void updateALineItem() throws Exception {
        Long lineItemId = 1L;
        lineItemResponseDto = new LineItemResponseDto();
        LineItemRequestDto lineItemRequestDto = LineItemRequestDto.builder()
                .projectedAmount(new BigDecimal("25000"))
                .budgetCategoryId(2L)
                .budgetId(5L)
                .build();

        given(lineItemServices.updateLineItem(lineItemRequestDto, lineItemId)).willReturn(ResponseEntity.ok(lineItemResponseDto));

        String requestBody = mapper.writeValueAsString(lineItemRequestDto);
        mockMvc.perform(put("/api/v1/user/line-items/update/{lineItemId}", lineItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}