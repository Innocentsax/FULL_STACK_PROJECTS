package com.example.cedarxpressliveprojectjava010.exception;

public class CartEmptyException extends RuntimeException{
    public CartEmptyException(String message){
        super(message);
    }
}
