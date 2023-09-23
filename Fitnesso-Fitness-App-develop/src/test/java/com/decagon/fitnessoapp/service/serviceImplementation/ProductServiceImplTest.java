package com.decagon.fitnessoapp.service.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fitnessoapp.dto.ProductResponseDto;
import com.decagon.fitnessoapp.repository.IntangibleProductRepository;
import com.decagon.fitnessoapp.repository.TangibleProductRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private IntangibleProductRepository intangibleProductRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @MockBean
    private TangibleProductRepository tangibleProductRepository;

    @Test
    void testViewProductDetails() {
        ProductResponseDto actualViewProductDetailsResult = this.productServiceImpl.viewProductDetails(123L,
                "Product Type");
        assertNull(actualViewProductDetailsResult);
        assertEquals("<400 BAD_REQUEST Bad Request,[]>", actualViewProductDetailsResult.toString());
    }

    @Test
    void testGetAllProducts() {
        when(this.tangibleProductRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.intangibleProductRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.productServiceImpl.getAllProducts(10).toList().isEmpty());
        verify(this.tangibleProductRepository).findAll();
        verify(this.intangibleProductRepository).findAll();
    }
}

