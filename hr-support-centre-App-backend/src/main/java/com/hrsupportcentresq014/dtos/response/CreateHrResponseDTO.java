package com.hrsupportcentresq014.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hrsupportcentresq014.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateHrResponseDTO {
    private String firstName;
    private String middleName;
    private String phoneNo;
    private String email;
    private String lastName;
    private String password;
    private Role role;
    private LocalDate dob;
    private String position;
    private String reportTo;
}
