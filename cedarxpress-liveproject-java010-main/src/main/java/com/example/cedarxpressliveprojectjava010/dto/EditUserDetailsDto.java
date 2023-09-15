package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.enums.Gender;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserDetailsDto {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Gender gender;
}
