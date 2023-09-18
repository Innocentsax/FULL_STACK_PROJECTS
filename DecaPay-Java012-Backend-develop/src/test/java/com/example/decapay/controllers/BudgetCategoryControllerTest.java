package com.example.decapay.controllers;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.utils.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BudgetCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class BudgetCategoryControllerTest {

    @MockBean
    private BudgetCategoryService budgetCategoryService;

    @MockBean
    private BudgetCategoryRepository budgetCategoryRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private UserUtil userUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void createBudgetCategory() throws Exception {
        BudgetCategoryRequest budgetCategoryRequest = new BudgetCategoryRequest();
        User user = new User();
        BudgetCategoryResponse budgetCategoryResponse = new BudgetCategoryResponse();


        given(userUtil.getAuthenticatedUserEmail()).willReturn("emailAddress");

        given(userRepository.findByEmail("mic@gmail.com")).willReturn(Optional.of(user));

        given(budgetCategoryService.createBudgetCategory(budgetCategoryRequest)).willReturn(budgetCategoryResponse);

        String requestBody = mapper.writeValueAsString(budgetCategoryRequest);

        mockMvc.perform(post("/api/v1/budgets/category/create")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }


    @Test
    void updateBudgetCategory() throws Exception {
        BudgetCategoryRequest budgetCategoryRequest = new BudgetCategoryRequest();
        User user = new User();
        BudgetCategoryResponse budgetCategoryResponse = new BudgetCategoryResponse();


        given(userUtil.getAuthenticatedUserEmail()).willReturn("emailAddress");

        given(userRepository.findByEmail("mic@gmail.com")).willReturn(Optional.of(user));

        given(budgetCategoryService.createBudgetCategory(budgetCategoryRequest)).willReturn(budgetCategoryResponse);

        String requestBody = mapper.writeValueAsString(budgetCategoryRequest);

        mockMvc.perform(put("/api/v1/budgets/category/update/{budgetCategoryId}", 1L)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void getBudgetCategory() throws Exception {
        User user = new User();
        when(userUtil.getAuthenticatedUserEmail()).thenReturn("emailAddress");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        when(budgetCategoryService.getBudgetCategory(anyLong())).thenReturn(new BudgetCategoryResponse());
        mockMvc.perform(get("/api/v1/budgets/category/{budgetCategoryId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(budgetCategoryService, times(1)).getBudgetCategory(1L);
    }

    @Test
    void getBudgetCategories() throws Exception {
        User user = new User();
        when(userUtil.getAuthenticatedUserEmail()).thenReturn("emailAddress");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        when(budgetCategoryService.getBudgetCategories()).thenReturn(List.of(new BudgetCategoryResponse()));
        mockMvc.perform(get("/api/v1/budgets/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(budgetCategoryService, times(1)).getBudgetCategories();
    }


    @Test
    void testDeleteBudgetCategory() throws Exception {

        long budgetCategoryId = 1L;

        mockMvc.perform(delete("/api/v1/budgets/category/{budgetCategoryId}", budgetCategoryId))
                .andExpect(status().isOk());
    }
}