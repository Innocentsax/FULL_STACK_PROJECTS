package com.hrsupportcentresq014.dtos.request;

import com.hrsupportcentresq014.entities.NextOfKin;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.utils.Social;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class EmployeeProfileRequest {
        @NotNull(message = "birthday must not be Blank")
        @Past(message = "Birthday must be in the past")
        private LocalDate birthday;
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
        private String address;
        private NextOfKin nextOfKin;
        private String maritalStatus;
        private String contractType;
        private String imageUrl;
        private String workLocation;
        private Role role;
        private String department;
        private String nationality;
    }



