package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.services.serviceImpl.ProductServiceImpl;
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

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    void viewASingleProduct() {
        try {
            Product product = new Product();
            product.setId(2L);
            product.setName("Tall dinning chair");
            product.setImageUrl("hgdhg");
            product.setColor("green");
            product.setPrice(2000.00);
            product.setDescription("strong black");
          ProductCustResponseDto productCustResponseDto = ProductCustResponseDto.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                    .color("green").description("strong black").build();
          when(productService.fetchASingleProduct(anyLong())).thenReturn(productCustResponseDto);
          mockMvc.perform(get("/api/v1/products/view/{product_id}", 2L)
                  .contentType("application/json"))
            .andExpect(status().isOk());
        } catch (Exception ce){
            ce.printStackTrace();
        }
    }
}
