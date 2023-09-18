package com.example.decapay.controllers;


import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.services.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(controllers = ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private ExpenseController expenseController;
    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ExpenseResponseDto expenseResponseDto;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser("seunskii")
    @Test
    void createExpense() {
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setAmount(new BigDecimal(5000));
        expenseRequestDto.setDescription("Food stuff");

        try {
            Long lineItemId = 1L;
            String requestBody = objectMapper.writeValueAsString(expenseRequestDto);
            mockMvc.perform(post("/api/v1/expense/create-expense/{lineId}", lineItemId)
                    .contentType(MediaType.APPLICATION_JSON).with(csrf()).content(requestBody)).andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @WithMockUser("innocent")
    @Test
    void updateExpense() throws Exception {

        Long expenseId = 1L;
        expenseResponseDto = new ExpenseResponseDto();
        ExpenseRequestDto expenseRequestDto = ExpenseRequestDto.builder()
                .amount(new BigDecimal("13000")).description("Expenses")
                .build();

        given(expenseService.updateExpense(expenseRequestDto, expenseId)).willReturn(expenseResponseDto);

        String requestBody = objectMapper.writeValueAsString(expenseRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/expense/update/{expenseId}", expenseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(requestBody))
                .andExpect(status().isOk());

    }
}