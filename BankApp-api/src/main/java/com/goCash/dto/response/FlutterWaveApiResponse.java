package com.goCash.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FlutterWaveApiResponse {
    private String status;
    private String message;
    private String accountNumber;
    private String accountName;

    public FlutterWaveApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}