package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePickUpCenterDto {
    private String name;
    private String address;
    private String email;
    private String phone;
}
