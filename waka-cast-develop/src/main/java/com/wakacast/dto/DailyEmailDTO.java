package com.wakacast.dto;

import com.wakacast.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyEmailDTO {
    private UserType userType;
    private String userPersona;
    private String emailSubject;
    private String emailContent;
}
