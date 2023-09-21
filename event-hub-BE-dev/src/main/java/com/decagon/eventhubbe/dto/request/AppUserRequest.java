package com.decagon.eventhubbe.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
}
