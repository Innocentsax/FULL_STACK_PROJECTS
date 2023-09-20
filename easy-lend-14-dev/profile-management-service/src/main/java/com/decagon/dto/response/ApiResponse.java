package com.decagon.dto.response;

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
public class ApiResponse<T> {
    private String message;
    private String time;
    private T data;
    public ApiResponse(T data) {
        this.message = "Processed Successfully";
        this.time = saveDate(LocalDateTime.now());
        this.data = data;
    }

    private static String saveDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
        return date.format(formatter);
    }
}
