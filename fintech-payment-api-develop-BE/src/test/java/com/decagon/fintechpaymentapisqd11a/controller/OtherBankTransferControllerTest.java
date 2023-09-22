package com.decagon.fintechpaymentapisqd11a.controller;

import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.services.TransferService;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OtherBankTransferController.class})
@ExtendWith(SpringExtension.class)
class OtherBankTransferControllerTest {
    @Autowired
    private OtherBankTransferController otherBankTransferController;

    @MockBean
    private TransferService transferService;

    @Test
    void getBanks() throws Exception {

        when(transferService.getAllBanks()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/transfer/banks");
        MockMvcBuilders.standaloneSetup(otherBankTransferController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

