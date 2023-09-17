package com.wakacast.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    private final String token;
    private final String message;

    public InvalidTokenRequestException(String token, String message) {
        super(String.format("[%s] token: [%s] ", message, token));
        this.token = token;
        this.message = message;
    }
}
