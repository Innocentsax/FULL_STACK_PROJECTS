package com.example.money_way.dto.request;

import com.example.money_way.enums.TvServiceProvider;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class TvVariationsRequest {
    @Enumerated(EnumType.STRING)
    private TvServiceProvider serviceProvider;
}
