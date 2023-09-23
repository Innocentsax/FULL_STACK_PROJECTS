package com.example.food.controllers;

import com.example.food.pojos.WalletResponse;
import com.example.food.services.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {WalletController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {
    @Autowired
    private WalletController walletController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;

    @Test
    @DisplayName("Display 10000 as wallet balance")
    void getBalanceTest() throws Exception {

        WalletResponse response = WalletResponse.builder()
                .walletBalance(BigDecimal.valueOf(10000.0))
                .build();
        Mockito.when(walletService.getWalletBalance()).thenReturn(response);

        mockMvc.perform(get("/wallet/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletBalance", is(Double.valueOf(response.getWalletBalance().toString()))));
    }
}

