package com.facebook.facebook.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String s) {
        System.out.println("No name");
    }
}
