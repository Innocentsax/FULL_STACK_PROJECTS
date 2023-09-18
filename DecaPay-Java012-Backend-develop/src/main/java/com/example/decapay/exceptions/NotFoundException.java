package com.example.decapay.exceptions;

/**
 * @author Ikechi Ucheagwu
 * @created 06/01/2023 - 13:49
 * @project DecaPay-Java012-Backend
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
