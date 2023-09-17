package com.decagon.safariwebstore.dto;

import com.decagon.safariwebstore.model.ShippingAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String quantity;

    private String deliveryMethod;

    private Double price;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateOrdered;

    private Double cardDiscount;


    private Date dateDelivered;

    private Double deliveryFee;

    private String paymentType;

    private String status;

    private Double costOfProducts; //alias subtotal

    private Boolean isGift;

    private Double totalCost;

    private ShippingAddress shippingAddress;

    private List<Long> cartItemIds;

}
