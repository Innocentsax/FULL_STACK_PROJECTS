package com.example.decapay.services.impl;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.models.Budget;
import com.example.decapay.models.BudgetCategory;
import com.example.decapay.models.LineItem;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.BudgetDto;
import com.example.decapay.pojos.requestDtos.CreateBudgetRequest;
import com.example.decapay.pojos.responseDtos.BudgetDtoResponse;
import com.example.decapay.pojos.responseDtos.BudgetViewModel;
import com.example.decapay.pojos.responseDtos.CreateBudgetResponse;
import com.example.decapay.repositories.BudgetRepository;
import com.example.decapay.repositories.LineItemRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.BudgetService;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.DateParser;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    private BudgetService budgetService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserUtil userUtil;

    @Mock
    private LineItemRepository lineItemRepository;

    User user;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetServiceImpl(budgetRepository,lineItemRepository, userService, userUtil);

        user = new User();
        user.setId(1L);
        user.setEmail("tester@email.com");
    }

    @Test
    @DisplayName("List of Budget for a User")
    void getBudgets() {
        List<Budget> budgets = createBudgetList();

        Pageable pageable = PageRequest.of(0, 1);
        int start = Math.min((int) pageable.getOffset(), budgets.size());
        int end = Math.min((start + pageable.getPageSize()), budgets.size());
        Page<Budget> budgetPage = new PageImpl<>(budgets.subList(start, end), pageable, budgets.size());

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("tester@email.com");
        when(userService.getUserByEmail("tester@email.com")).thenReturn(user);
        when(budgetRepository.findAllByUser(user, pageable)).thenReturn(budgetPage);
        when(lineItemRepository.findAllByBudget(any(Budget.class))).thenReturn(createLineItemList());

        List<BudgetViewModel> budgetViewModel = budgetService.getBudgets(0, 1);

        assertNotNull(budgetViewModel);
        assertEquals(1, budgetViewModel.size());
        assertEquals(new BigDecimal("60.0"), budgetViewModel.get(0).getPercentage());
        assertEquals(new BigDecimal("94.0"), budgetViewModel.get(0).getLineItemRests().get(0).getPercentageSpentSoFar());
        verify(budgetRepository, times(1)).findAllByUser(any(User.class), any(Pageable.class));
    }

    private List<Budget> createBudgetList() {
        Budget budget = new Budget();
        budget.setDescription("Budget-One");
        budget.setAmount(new BigDecimal(2000));

        List<Budget> budgets = new ArrayList<>();
        budgets.add(budget);

        return budgets;
    }

    private List<LineItem> createLineItemList() {
        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setName("House");

        LineItem lineItem = new LineItem();
        lineItem.setProjectedAmount(new BigDecimal(800));
        lineItem.setBudgetCategory(budgetCategory);
        lineItem.setTotalAmountSpent(new BigDecimal(750));

        LineItem lineItem2 = new LineItem();
        lineItem2.setProjectedAmount(new BigDecimal(500));
        lineItem2.setTotalAmountSpent(new BigDecimal(450));
        lineItem2.setBudgetCategory(budgetCategory);

        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem);
        lineItems.add(lineItem2);

        return lineItems;
    }

    @Test
    void testCreateBudget() {
        User activeUser = new User();
        activeUser.setId(2L);
        activeUser.setEmail("mybudget@email.com");
        userRepository.save(activeUser);
        LocalDate startDate = DateParser.parseDate("2022-12-19");
        LocalDate endDate = DateParser.parseDate("2023-01-19");
        Budget budget = new Budget();
        budget.setAmount(BigDecimal.valueOf(1000));
        budget.setBudgetPeriod(BudgetPeriod.CUSTOM);
        budget.setTitle("Test");
        budget.setId(2L);
        budget.setDescription("Testing....");
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setUser(activeUser);
        given(userUtil.getAuthenticatedUserEmail()).willReturn("mybudget@email.com");
        given(userService.getUserByEmail("mybudget@email.com")).willReturn(activeUser);
        CreateBudgetRequest budgetRequest = CreateBudgetRequest.mapBudgetToCreateBudgetRequest(budget);
        CreateBudgetResponse returnedBudget = budgetService.createBudget(budgetRequest);
        assertEquals(budgetRequest.getAmount(), returnedBudget.getAmount());
        assertEquals(budgetRequest.getTitle(), returnedBudget.getTitle());
        assertEquals(budgetRequest.getPeriod(), returnedBudget.getPeriod());
        assertEquals(budgetRequest.getDescription(), returnedBudget.getDescription());
    }

    @Test
    void testFetchBudget() {
        User activeUser = new User();
        activeUser.setId(2L);
        activeUser.setEmail("mybudget@email.com");
        userRepository.save(activeUser);
        Budget budget = new Budget();
        budget.setId(2L);
        budget.setUser(activeUser);
        budgetRepository.save(budget);
        given(userUtil.getAuthenticatedUserEmail()).willReturn("mybudget@email.com");
        given(userService.getUserByEmail("mybudget@email.com")).willReturn(activeUser);
        given(budgetRepository.findBudgetById(2L)).willReturn(Optional.of(budget));
        BudgetViewModel returnedBudget = budgetService.fetchBudgetById(2L);
        assertEquals(budget.getAmount(), returnedBudget.getAmount());
        assertEquals(budget.getId(), returnedBudget.getBudgetId());
        assertEquals(budget.getAmount(), returnedBudget.getAmount());
    }

    @Test
    void testDeleteBudget() {

        Budget budget = new Budget();
        budget.setId(1L);
        budget.setUser(user);

        //given
        given(userUtil.getAuthenticatedUserEmail()).willReturn("tester@email.com");
        given(userService.getUserByEmail("tester@email.com")).willReturn(user);
        given(budgetRepository.findById(1L)).willReturn(Optional.of(budget));

        //when
        budgetService.deleteBudget(1L);

        //then
        verify(budgetRepository).delete(budget);
    }


    @Test
    void testUpdateBudget() {
        User activeUser = new User();
        activeUser.setId(1L);
        activeUser.setEmail("updatebudget@email.com");
        userRepository.save(activeUser);
        LocalDate startDate = DateParser.parseDate("2022-12-19");
        LocalDate endDate = DateParser.parseDate("2023-01-19");
        Budget budget = new Budget();
        budget.setAmount(BigDecimal.valueOf(1000));
        budget.setBudgetPeriod(BudgetPeriod.CUSTOM);
        budget.setTitle("Test");
        budget.setId(1L);
        budget.setDescription("Testing....");
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setUser(activeUser);
        //given
        given(userUtil.getAuthenticatedUserEmail()).willReturn("updatebudget@email.com");
        given(userService.getUserByEmail("updatebudget@email.com")).willReturn(activeUser);
        given(budgetRepository.findById(1L)).willReturn(Optional.of(budget));
        BudgetDto budgetDto = new BudgetDto();
       budgetDto.setTitle(budget.getTitle());
       budgetDto.setAmount(budget.getAmount());
       budgetDto.setDescription(budget.getDescription());
       budgetDto.setBudgetPeriod(String.valueOf(budget.getBudgetPeriod()));
       budgetDto.setStartDate(String.valueOf(budget.getStartDate()));
       budgetDto.setUpdatedAt(LocalDateTime.now());
       budgetDto.setEndDate(String.valueOf(budget.getEndDate()));
        System.out.println(budgetDto);
        budgetService.updateBudget(budgetDto, 1L);
        BudgetDtoResponse dtoResponse = new BudgetDtoResponse();
        dtoResponse.setTitle(budgetDto.getTitle());
        dtoResponse.setAmount(budgetDto.getAmount());
        dtoResponse.setPeriod(String.valueOf(budgetDto.getBudgetPeriod()));
        dtoResponse.setDescription(budgetDto.getDescription());
        System.out.println(dtoResponse);
        verify(budgetRepository).save(budget);
        assertEquals(budgetDto.getAmount(), dtoResponse.getAmount());
        assertEquals(budgetDto.getTitle(), dtoResponse.getTitle());
        assertEquals(budgetDto.getDescription(), dtoResponse.getDescription());
    }
}