package com.example.cedarxpressliveprojectjava010.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String error){
        super(error);
    }
}
