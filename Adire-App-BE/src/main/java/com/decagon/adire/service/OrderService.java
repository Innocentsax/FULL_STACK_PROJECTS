package com.decagon.adire.service;

import com.decagon.adire.dto.request.OrderDTO;
import com.decagon.adire.dto.response.*;
import com.decagon.adire.dto.request.OrderDTO;
import com.decagon.adire.entity.Order;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderDTO requestDTO, HttpServletRequest request);


    RevenueReportResponse getAllRevenueReport(HttpServletRequest request);

    OrderDTO findOrderById(String id);

    List<Report> getMonthlyReport(HttpServletRequest request, Principal principal);

    PaginationResponse<OrderDTO> fetchAllOrders(HttpServletRequest request, int pageNo, int pageSize, String sortBy, String sortDir);

    int countOrders(HttpServletRequest request);
    OrderResponse updateOrder(OrderDTO orderDto, String id);
    void deleteOrder(String id);
}
