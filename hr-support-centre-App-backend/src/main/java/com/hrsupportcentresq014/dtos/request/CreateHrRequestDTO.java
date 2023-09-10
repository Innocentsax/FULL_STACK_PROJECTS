package com.hrsupportcentresq014.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHrRequestDTO {
    @NotBlank(message = "First Name is Mandatory")
    private String firstName;
    @NotBlank(message = "Middle Name is Mandatory")
    private String middleName;
    @NotBlank(message = "Last Name is Mandatory")
    private String lastName;
    @Size(min = 11, max = 14, message = "phone number must have  a minimum length of 11 and maximum of 15")
    private String phoneNo;
    @NotBlank(message = "email name is mandatory")
    @Email(message = "Enter a valid email", regexp = "(^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$)")
    private String email;
    private UUID uuid = UUID.randomUUID();
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String position;
    private String reportTo;
}
