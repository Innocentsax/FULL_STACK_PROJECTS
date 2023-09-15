package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.entity.OrderItem;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<OrderItem> customerOrder = new ArrayList<>();
    private LocalDateTime timeOfCompletion;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;
    private Double deliveryFee;
    private Double price;
    private String address;
    private User user;

}
