package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStaffResponse {
    private String firstName;

    private String lastName;

    private String email;

    private String position;

    private Role role;

    private String teamManager;

    private String contractType;

    private String workLocation;

    private String password;

    private LocalDate startDate;
}