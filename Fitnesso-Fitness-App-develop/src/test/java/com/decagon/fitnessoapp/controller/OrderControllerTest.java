//package com.decagon.fitnessoapp.controller;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.anyInt;
//import static org.mockito.Mockito.when;
//
//import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
//import com.decagon.fitnessoapp.service.OrderService;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ContextConfiguration(classes = {OrderController.class})
//@ExtendWith(SpringExtension.class)
//class OrderControllerTest {
//    @Autowired
//    private OrderController orderController;
//
//    @MockBean
//    private OrderService orderService;
//
//    @Test
//    void testViewAllOrders() throws Exception {
//        when(this.orderService.getAllOrders(anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/admin/viewOrders/{pageNo}", 1);
//        MockMvcBuilders.standaloneSetup(this.orderController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
//    }
//
//    @Test
//    void testViewOrdersByStatus() throws Exception {
//        when(this.orderService.getOrdersByStatus((ORDER_STATUS) any(), anyInt()))
//                .thenReturn(new PageImpl<>(new ArrayList<>()));
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/order/admin/viewOrdersBy/{status}/{pageNo}", ORDER_STATUS.PENDING, 1);
//        MockMvcBuilders.standaloneSetup(this.orderController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
//    }
//}
//
