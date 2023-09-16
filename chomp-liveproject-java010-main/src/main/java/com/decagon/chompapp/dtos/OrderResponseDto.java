package com.decagon.chompapp.dtos;


import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.ShippingAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@ToString
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderResponseDto {


    private Long orderId;


    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateOrdered;

    private Double flatRate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    private Double subTotal;

    private Double total;

    private ShippingAddress shippingAddress;

    private List<Product> productList;

}
