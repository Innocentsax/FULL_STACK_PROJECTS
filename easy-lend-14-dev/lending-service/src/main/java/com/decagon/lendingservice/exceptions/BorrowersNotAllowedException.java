package com.decagon.lendingservice.exceptions;

public class BorrowersNotAllowedException extends  RuntimeException{
    public  BorrowersNotAllowedException(String message){super(message);}
}
