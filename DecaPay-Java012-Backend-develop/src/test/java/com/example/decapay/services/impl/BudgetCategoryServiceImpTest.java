package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetCategoryRequest;
import com.example.decapay.pojos.responseDtos.BudgetCategoryResponse;
import com.example.decapay.repositories.BudgetCategoryRepository;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetCategoryService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetCategoryServiceImpTest {

    @Autowired
    private BudgetCategoryService budgetCategoryService;

    @Mock
    private BudgetCategoryRepository budgetCategoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserIdUtil userIdUtil;
    @Mock
    private UserUtil userUtil;

    @Mock
    private UserService userService;

    private BudgetCategoryRequest budgetCategoryRequest;

    private BudgetCategory budgetCategory;

    @BeforeEach
    void setUp() {
        budgetCategoryService = new BudgetCategoryServiceImp(
                budgetCategoryRepository, userRepository, userUtil, userService
        );
        budgetCategoryRequest = new BudgetCategoryRequest();
        budgetCategoryRequest.setName("Food Stuff");
        budgetCategory = new BudgetCategory();

    }

    @Test
    void createBudgetCategory() {
        User user = new User();

        LocalDateTime localDateTime = LocalDateTime.now();
        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setId(1L);
        budgetCategory.setUser(user);
        budgetCategory.setName("Provision");
        budgetCategory.setCreatedAt(localDateTime);
        budgetCategory.setUpdatedAt(localDateTime);

        String email = "mic@gmail.com";

        given(userUtil.getAuthenticatedUserEmail()).willReturn(email);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        given(budgetCategoryRepository.save(any(BudgetCategory.class))).willReturn(budgetCategory);

        budgetCategoryService.createBudgetCategory(budgetCategoryRequest);

    }

    @Test
    void updateBudgetCategory() {

        User user = new User();


        String email = "mic@gmail.com";
        budgetCategory.setId(1L);


        given(userUtil.getAuthenticatedUserEmail()).willReturn(email);

//        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(budgetCategoryRepository.findById(1L)).willReturn(Optional.of(budgetCategory));

        budgetCategoryService.updateBudgetCategory(1L, budgetCategoryRequest);
    }

    @Test
    void getBudgetCategory() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testing@gmail.com");

        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setId(2L);
        budgetCategory.setUser(user);
        budgetCategory.setName("Provision");
        budgetCategory.setIsDeleted(true);
        budgetCategory.setCreatedAt(LocalDateTime.now());
        budgetCategory.setUpdatedAt(LocalDateTime.now());

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("testing@gmail.com");
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(budgetCategoryRepository.findById(anyLong())).thenReturn(Optional.of(budgetCategory));

        BudgetCategoryResponse response = budgetCategoryService.getBudgetCategory(2L);
        assertNotNull(response);
        verify(budgetCategoryRepository, times(1)).findById(2L);
    }

    @Test
    void getBudgetCategories() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testing@gmail.com");

        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setId(2L);
        budgetCategory.setUser(user);
        budgetCategory.setName("Provision");
        budgetCategory.setIsDeleted(true);
        budgetCategory.setCreatedAt(LocalDateTime.now());
        budgetCategory.setUpdatedAt(LocalDateTime.now());

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("testing@gmail.com");
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(budgetCategoryRepository.findByUser(any(User.class))).thenReturn(List.of(budgetCategory));

        List<BudgetCategoryResponse> responses = budgetCategoryService.getBudgetCategories();
        assertNotNull(responses);
        verify(budgetCategoryRepository, times(1)).findByUser(user);
    }

    @Test
    final void testDeleteBudgetCategory() {
        User user = new User();
        BudgetCategory budgetCategory = new BudgetCategory();

        user.setId(1L);
        ;
        user.setEmail("testing@gmail.com");

        budgetCategory.setId(1L);
        budgetCategory.setUser(user);

        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(userUtil.getAuthenticatedUserEmail()).thenReturn("testing@gmail.com");
        when(budgetCategoryRepository.findById(1L)).thenReturn(Optional.of(budgetCategory));

        budgetCategoryService.deleteBudgetCategory(budgetCategory.getId());
//        verify(budgetCategoryRepository).delete(budgetCategory);
        verify(budgetCategoryRepository, times(1)).save(any(BudgetCategory.class));
    }
}
