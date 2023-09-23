package com.example.food.pojos;

import com.example.food.Enum.DeliveryMethod;
import com.example.food.Enum.OrderStatus;
import com.example.food.Enum.PaymentMethod;
import com.example.food.dto.CartItemDto;
import com.example.food.model.Address;
import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponse extends BaseResponse {
    private Long id;
    private String imageUrl;
    private Date createdAt;
    private Date modifiedAt;
    private Integer quantity;
    private OrderStatus orderStatus;
    private List<CartItemDto> cartItemDtoList;
    private Address address;
    private PaymentMethod paymentMethod;
    private BigDecimal deliveryFee;
    private BigDecimal discount;
    private DeliveryMethod deliveryMethod;
    private BigDecimal totalOrderPrice;
}