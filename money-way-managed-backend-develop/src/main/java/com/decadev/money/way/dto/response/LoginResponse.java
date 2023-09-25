package com.decadev.money.way.dto.response;

import com.decadev.money.way.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private Date expiredAt;
}
