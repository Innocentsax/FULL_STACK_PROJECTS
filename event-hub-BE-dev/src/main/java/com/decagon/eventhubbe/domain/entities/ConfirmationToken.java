package com.decagon.eventhubbe.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    private String id;
    @Indexed(unique = true)
    private String token;
    private Date expiresAt;
    @DBRef
    private AppUser appUser;
    public ConfirmationToken(String token, AppUser appUser){
        this.token = token;
        this.appUser = appUser;
        this.expiresAt = getExpirationDate();
    }
    private Date getExpirationDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,5);
        return new Date(calendar.getTime().getTime());
    }
}