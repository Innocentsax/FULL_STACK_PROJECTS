package com.easyLend.userservice.event;

import com.easyLend.userservice.domain.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegisterEvent  extends ApplicationEvent {
    private AppUser appUser;
    private String url;

    public RegisterEvent(AppUser appUser,String url){
        super(appUser);
        this.appUser=appUser;
        this.url=url;
    }


}
