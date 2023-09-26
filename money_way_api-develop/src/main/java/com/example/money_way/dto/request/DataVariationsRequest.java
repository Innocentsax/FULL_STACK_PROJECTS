package com.example.money_way.dto.request;

import com.example.money_way.enums.DataServiceProvider;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class DataVariationsRequest {
    @Enumerated(EnumType.STRING)
    private DataServiceProvider serviceProvider;
}
