package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.enums.Role;
import com.hrsupportcentresq014.entities.entityUtil.Socials;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class StaffProfileDTO {

    private String firstname;

    private String nickname;

    private String phoneNo;


    private String email;

    private String lastName;

    private String password;

    private Role role;

    private String department;

    private LocalDate startDate;

    private String workLocation;

    private String contractType;

    private Socials social;

    private String pictureUrl;

    private String resumeUrl;

    private LocalDate dob;

    private String position;

    private String teamManager;

}
