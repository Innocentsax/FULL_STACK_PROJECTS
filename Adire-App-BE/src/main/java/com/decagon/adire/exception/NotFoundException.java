package com.decagon.adire.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class NotFoundException extends  RuntimeException {
    private String message;
    private String debugMessage;


    public NotFoundException(String message) {
        this.message = message;
    }

    public NotFoundException(String message, String debugMessage) {

        super(message);
        this.debugMessage = debugMessage;
    }
}
