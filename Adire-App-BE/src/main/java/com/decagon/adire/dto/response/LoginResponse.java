package com.decagon.adire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String firstName;

    private  String lastName;

    private String email;

    private  String phoneNumber;
    private String url;
    private String token;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
