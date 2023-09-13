package com.decagon.adire.dto.response;


import com.decagon.adire.dto.request.MeasurementDto;
import com.decagon.adire.entity.Customer;
import com.decagon.adire.enums.MaterialType;
import com.decagon.adire.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private String customerEmail;
    private CustomerResponseDTO customer;
    private String materialType;
    private List<MeasurementDto> measurementList;
    private String duration;
    private  String designImageUrl;
    private  Double orderPrice;
    private LocalDateTime dueDate;
    private String orderStatus;
}
