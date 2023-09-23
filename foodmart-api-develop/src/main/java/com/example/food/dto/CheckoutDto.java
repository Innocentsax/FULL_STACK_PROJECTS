package com.example.food.dto;

import com.example.food.Enum.DeliveryMethod;
import com.example.food.Enum.PaymentMethod;
import com.example.food.model.Address;
import lombok.Data;

@Data
public class CheckoutDto {
    private DeliveryMethod deliveryMethod;
    private String streetAddress;
    private String city;
    private String state;
}
