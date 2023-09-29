package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.PickupCenter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateResponse {
    private Long id;
    private String name;
    private Set<PickupCenter> pickupCenters;
}
