package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.serviceImpl.AdminServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AdminServiceImpl adminService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    void viewASingleProduct() {
        try {
            SubCategory subCategory = new SubCategory();
            Product product = new Product();
            product.setId(2L);
            product.setName("Tall dinning chair");
            product.setImageUrl("hgdhg");
            product.setColor("green");
            product.setPrice(2000.00);
            product.setDescription("strong black");
            ProductResponseDto productResponseDto = ProductResponseDto.builder().name("Tall dinning chair")
                    .price("#2000.00").imageUrl("hgdhg").availableQty(3)
                    .subCategory(subCategory).color("green").description("strong black").build();
            when(adminService.fetchASingleProduct(anyLong())).thenReturn(productResponseDto);
            mockMvc.perform(get("/api/v1/admin/products/{product_id}", 2L)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception ce){
            ce.printStackTrace();
        }
    }
}