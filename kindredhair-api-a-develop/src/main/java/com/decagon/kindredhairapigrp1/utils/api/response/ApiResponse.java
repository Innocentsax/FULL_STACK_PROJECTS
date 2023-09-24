package com.decagon.kindredhairapigrp1.utils.api.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.Data;

/**
 * ApiResponse
 */
@Data
public class ApiResponse<T> {

  private int status;
  private String message;
  private String error;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String debugMessage;
  private List<ApiSubError> subErrors;
  private T data;

  public ApiResponse() {
    timestamp = LocalDateTime.now();
  }

  public ApiResponse(HttpStatus status) {
    this();
    this.status = status.value();
  }

  public ApiResponse(HttpStatus status, Throwable ex) {
    this(status);
    this.error = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiResponse(HttpStatus status, String message, Throwable ex) {
    this(status);
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  private void addSubError(ApiSubError subError) {
    if (subErrors == null) {
      subErrors = new ArrayList<>();
    }
    subErrors.add(subError);
  }

  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addSubError(new ApiValidationError(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addSubError(new ApiValidationError(object, message));
  }

  private void addValidationError(FieldError fieldError) {
    this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
            fieldError.getDefaultMessage());
  }

  public void addValidationErrors(List<FieldError> fieldErrors) {
    fieldErrors.forEach(this::addValidationError);
  }

  private void addValidationError(ObjectError objectError) {
    this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
  }

  public void addValidationError(List<ObjectError> globalErrors) {
    globalErrors.forEach(this::addValidationError);
  }

}
