package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.NextOfKin;
import com.hrsupportcentresq014.utils.Social;
import lombok.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeProfileResponse {
    private String email;
    private String imageUrl;
    private String resumeUrl;
    private LocalDate birthday;
    private String address;
    private NextOfKin nextOfKin;
    private Social social;
    private String nationality;
    private String maritalStatus;
}
