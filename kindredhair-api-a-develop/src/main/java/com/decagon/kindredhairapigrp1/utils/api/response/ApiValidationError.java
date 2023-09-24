package com.decagon.kindredhairapigrp1.utils.api.response;

import lombok.Data;

@Data
public class ApiValidationError extends ApiSubError{
  private String object;
  private String field;
  private Object rejectedValue;
  private String message;

  ApiValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }

  public ApiValidationError(String object, String field, Object rejectedValue, String message) {
    this(object, message);
    this.rejectedValue = rejectedValue;
    this.field = field;
  }

}
