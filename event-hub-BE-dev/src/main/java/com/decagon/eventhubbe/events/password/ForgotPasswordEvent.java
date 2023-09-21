package com.decagon.eventhubbe.events.password;

import com.decagon.eventhubbe.domain.entities.AppUser;
import lombok.*;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ForgotPasswordEvent extends ApplicationEvent {
    private AppUser appUser;
    private String applicationUrl;
    public ForgotPasswordEvent(AppUser appUser, String applicationUrl){
        super(appUser);
        this.appUser = appUser;
        this.applicationUrl = applicationUrl;
    }
}
