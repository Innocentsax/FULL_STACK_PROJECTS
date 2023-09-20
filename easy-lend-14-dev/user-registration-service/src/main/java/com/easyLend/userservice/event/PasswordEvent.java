package com.easyLend.userservice.event;

import com.easyLend.userservice.domain.entity.AppUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter

public class PasswordEvent extends ApplicationEvent {
    private AppUser appUser;
    private String url;
    public PasswordEvent(AppUser appUser,String url){
        super(appUser);
        this.appUser=appUser;
        this.url=url;
    }
}
