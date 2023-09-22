package com.decagon.fintechpaymentapisqd11a.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.dto.LocalBankTransferDto;
import com.decagon.fintechpaymentapisqd11a.services.serviceImpl.LocalTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LocalTransferController.class})
@ExtendWith(SpringExtension.class)
class LocalTransferControllerTest {
    @Autowired
    private LocalTransferController localTransferController;

    @MockBean
    private LocalTransferServiceImpl localTransferServiceImpl;

    @Test
    void testLocalTransfer() throws Exception {
        when(localTransferServiceImpl.localTransfer((LocalBankTransferDto) any())).thenReturn("Local Transfer");

        LocalBankTransferDto localBankTransferDto = new LocalBankTransferDto();
        localBankTransferDto.setAccountNumber("42");
        localBankTransferDto.setAmount(BigDecimal.valueOf(42L));
        localBankTransferDto.setBankName("Bank Name");
        localBankTransferDto.setNarration("Narration");
        localBankTransferDto.setPin("Pin");
        String content = (new ObjectMapper()).writeValueAsString(localBankTransferDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/transfer/localTransfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(localTransferController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Local Transfer"));
    }
}

