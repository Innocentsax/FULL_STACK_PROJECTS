package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.PickupCenter;
import com.decagon.OakLandv1be.entities.Transaction;
import com.decagon.OakLandv1be.enums.PickupStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminOrderResponseDto {
    private String firstName;
    private String lastName;
    private String phone;
    private Double grandTotal;
    private String status;
    private String pickupStatus;
    private String pickupCenterName;
    private String pickupCenterAddress;

}
