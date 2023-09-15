package com.example.cedarxpressliveprojectjava010.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {
    private String timestamp = new Date().toString();
	private boolean status;
    private String message;
    private T data;

    public ApiResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

	public ApiResponse() {
	}

    public ApiResponse(T data) {
        this.data = data;
        this.message = "SUCCESS";
        this.status = true;
    }


}