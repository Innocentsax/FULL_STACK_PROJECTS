package com.example.food.pojos;

import com.example.food.model.Order;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewAllOrderResponse extends BaseResponse {
    private List<Order> listOfOrders;
}
