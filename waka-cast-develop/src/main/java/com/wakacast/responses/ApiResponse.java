package com.wakacast.responses;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {

    private Map<String, String> data;
    private String message;
    private boolean error = true;

    public ApiResponse(Map<String, String> data, String message){
        this.data = data;
        this.message = message;
    }
}
