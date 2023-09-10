package com.hrsupportcentresq014.exceptions;


public class JobPostingNotFoundException extends RuntimeException{
    public JobPostingNotFoundException(String message){
        super(message);
    }
}
