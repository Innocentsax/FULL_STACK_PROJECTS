package com.example.cedarxpressliveprojectjava010.exception;

import lombok.Data;
import java.util.Date;

@Data
public class ApiErrorDetail {
    private Date timeStamp;
    private String message;
    private String details;

    public ApiErrorDetail(String message, String details){
        this.message =message;
        this.details = details;
        this.timeStamp = new Date();
    }
}
