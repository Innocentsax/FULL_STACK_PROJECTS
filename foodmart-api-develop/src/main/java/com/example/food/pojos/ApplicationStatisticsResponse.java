package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
import lombok.Data;

@Data
public class ApplicationStatisticsResponse extends BaseResponse {
    private ApplicationDetails applicationDetails;
}
