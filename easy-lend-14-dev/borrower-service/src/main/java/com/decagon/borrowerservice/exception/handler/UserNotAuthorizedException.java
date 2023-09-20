package com.decagon.borrowerservice.exception.handler;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String permissionDenied) {
        super(permissionDenied);
    }
}
