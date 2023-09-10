package com.hrsupportcentresq014.dtos.response;


import com.hrsupportcentresq014.entities.Role;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AdminResponse {
    private String firstName;
    private String nickName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private Role role;
    private String position;
}
