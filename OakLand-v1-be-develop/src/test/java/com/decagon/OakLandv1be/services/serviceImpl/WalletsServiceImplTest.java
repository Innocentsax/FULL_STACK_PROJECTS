package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Wallet;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.WalletRepository;
import com.decagon.OakLandv1be.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletsServiceImplTest {

    @Mock
    WalletRepository walletRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    WalletServiceImpl walletService;


    FundWalletResponseDto fundWalletResponseDto;

    Customer customer;


    Integer pageNo = 0;
    Integer pageSize = 10;
    String sortBy = "depositAmount";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder()
                .person(Person.builder().firstName("John").lastName("Doe").build())
                .wallet(Wallet.builder().accountBalance(BigDecimal.valueOf(100.00)).build())
                .build();
    }

    @Test
    void viewCustomerWalletByPagination() {
        List<Customer> customerList = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customerList);
        Page<FundWalletResponseDto> result = walletService.viewCustomerWalletByPagination(pageNo, pageSize, sortBy);
        List<FundWalletResponseDto> wallets = result.getContent();
        assertEquals(1, wallets.size());
        assertEquals("John Doe", wallets.get(0).getFullName());
        assertEquals(BigDecimal.valueOf(100.00), wallets.get(0).getDepositAmount());
        assertEquals(1, result.getTotalElements());
        verify(customerRepository, times(1)).findAll();
    }
}
