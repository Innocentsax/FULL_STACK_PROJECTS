package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.TransactionDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.EmptyListException;
import com.decagon.OakLandv1be.repositries.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionInitServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;


    @InjectMocks
    TransactionInitServiceImpl transactionInitService;

    private Transaction transaction;
    private TransactionDto transactionDto;
    List<Transaction> transactions;
    List<TransactionDto> transactionDtos;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactions = Arrays.asList(new Transaction(), new Transaction());
        transactionDtos = Arrays.asList(new TransactionDto(), new TransactionDto(), new TransactionDto());
        when(transactionRepository.findAll()).thenReturn(transactions);
    }

    @Test
    void viewAllTransactionsPaginated() {
        Page<TransactionDto> result = transactionInitService.viewAllTransactionsPaginated(0, 16, "id", false);
        assertEquals(16, result.getSize());
        assertEquals(2, result.getNumberOfElements());
        verify(transactionRepository).findAll();
    }

    @Test
    public void testViewAllTransaction_EmptyList() {
        List<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactionList);
        assertThrows(EmptyListException.class, () -> transactionInitService.viewAllTransactionsPaginated(0, 2, "id", true));
        verify(transactionRepository, times(1)).findAll();
    }

}