package com.example.cedarxpressliveprojectjava010.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;


}
