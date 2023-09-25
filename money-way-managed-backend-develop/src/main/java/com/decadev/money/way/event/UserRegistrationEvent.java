package com.decadev.money.way.event;

import com.decadev.money.way.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegistrationEvent extends ApplicationEvent {
    private User user;
    private String registrationConfirmationUrl;
    public UserRegistrationEvent(User user, String registrationConfirmationUrl) {
        super(user);
        this.user = user;
        this.registrationConfirmationUrl  = registrationConfirmationUrl;
    }
}
