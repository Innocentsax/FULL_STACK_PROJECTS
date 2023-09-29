package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.enums.BaseCurrency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDetailsRequest {
    private String email;
    private String password;
    private BaseCurrency baseCurrency;
}
