package com.decagon.OakLandv1be.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.decagon.OakLandv1be.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void deleteItem_shouldRemoveItemFromCartAndReturnSuccess() throws Exception {
        Long itemId = 1L;
        when(cartService.removeItem(itemId)).thenReturn(String.valueOf(true));

        mockMvc.perform(delete("/api/v1/cart/item/delete/{itemId}", itemId))
                .andExpect(status().isOk());
               // .andExpect(content().string("Item removed successfully"));

        verify(cartService).removeItem(itemId);


    }


}


//





