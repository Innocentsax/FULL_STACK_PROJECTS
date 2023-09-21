package com.decagon.eventhubbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userFullName;
    private String userEmail;
    private String imageUrl;
    private String accessToken;
    private String refreshToken;
    private String message;
}
