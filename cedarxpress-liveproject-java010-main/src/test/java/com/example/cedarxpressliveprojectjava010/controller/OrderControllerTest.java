package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.dto.WalletDto;
import com.example.cedarxpressliveprojectjava010.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @Autowired
    OrderController orderController;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test: view Particular User Order")
    void should_viewParticularUserOrder() throws Exception {

        OrderDto orderDto = OrderDto.builder()
                .price(9000.00)
                .build();
        when(orderService.viewParticularUserOrder(1L, 1L))
                .thenReturn(new ResponseEntity<>(orderDto, HttpStatus.OK));

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.orderController)
                .build()
                .perform(MockMvcRequestBuilders.get("/orders/1/1"));
        actualPerformResult.andExpect(status().is(200));
    }
}