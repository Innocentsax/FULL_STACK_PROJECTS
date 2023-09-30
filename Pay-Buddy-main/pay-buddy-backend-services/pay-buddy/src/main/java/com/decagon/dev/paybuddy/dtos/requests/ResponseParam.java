package com.decagon.dev.paybuddy.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseParam {
    private String key;
    private String value;
}
