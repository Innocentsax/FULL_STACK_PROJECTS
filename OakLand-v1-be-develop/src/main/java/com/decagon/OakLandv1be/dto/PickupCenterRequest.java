package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickupCenterRequest {
    private String name;
    private String location;
    private String state;
    private String email;
    private String phone;
    private Double delivery;
}
