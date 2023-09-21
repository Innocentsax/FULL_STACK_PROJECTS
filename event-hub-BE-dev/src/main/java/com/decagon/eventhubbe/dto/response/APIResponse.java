package com.decagon.eventhubbe.dto.response;

import com.decagon.eventhubbe.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"message", "time", "data"})
public class APIResponse<T> {
    private String message;
    private String time;
    private T data;
    public APIResponse(T data) {
        this.message = "Processed Successfully";
        this.time = DateUtils.saveDate(LocalDateTime.now());
        this.data = data;
    }


    // Rest of the class...
}

