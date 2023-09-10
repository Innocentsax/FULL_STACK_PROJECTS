package com.hrsupportcentresq014.exceptions;


public class JobPostingExistsException extends RuntimeException{
    public JobPostingExistsException(String message){
        super(message);
    }
}
