package com.hrsupportcentresq014.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrsupportcentresq014.entities.NextOfKin;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.utils.Social;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeViewProfileResponse {
    private String firstName;
    private String lastName;
    private String nickName;
    private String position;
    private String phoneNo;
    private String email;
    private LocalDate startDate;
    private Social social;
    private String reportTo;
    private String resumeUrl;
    private LocalDate birthday;
    private String address;
    private NextOfKin nextOfKin;
    private String maritalStatus;
    private String contractType;
    private String imageUrl;
    private String workLocation;
    private Role role;
    private String department;
    private String nationality;
    private LocalDateTime createdOn;
}
