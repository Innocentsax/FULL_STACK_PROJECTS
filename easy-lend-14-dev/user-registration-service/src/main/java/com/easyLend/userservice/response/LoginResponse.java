package com.easyLend.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private String email;
    private String accessToken;
    private Boolean activate;
    private String refreshToken;
    private Integer registrationStage;
    private Boolean registrationStatus;
}
