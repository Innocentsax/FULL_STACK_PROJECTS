package com.decagon.eventhubbe.events.register;

import com.decagon.eventhubbe.domain.entities.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private AppUser appUser;
    private String applicationUrl;
    public RegistrationEvent(AppUser appUser, String applicationUrl){
        super(appUser);
        this.appUser = appUser;
        this.applicationUrl = applicationUrl;
    }

}
