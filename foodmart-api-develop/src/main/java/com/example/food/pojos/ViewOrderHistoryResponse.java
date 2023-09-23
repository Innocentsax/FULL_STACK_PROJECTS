package com.example.food.pojos;

import com.example.food.model.Order;
import com.example.food.model.Product;
import com.example.food.restartifacts.BaseResponse;
import lombok.Builder;

import java.util.List;
@Builder
public class ViewOrderHistoryResponse extends BaseResponse {
    private List<Order> orderList;
    private Long numberOfOrders;
}
