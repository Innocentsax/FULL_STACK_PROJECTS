package com.goCash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FlutterWaveAccountResponse {
    private String status;
    private String message;
    private FlutterWaveData data;

}
