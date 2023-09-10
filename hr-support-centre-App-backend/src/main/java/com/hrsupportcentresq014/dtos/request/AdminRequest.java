package com.hrsupportcentresq014.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequest {

    @NotBlank(message = "Invalid: First name cannot be blank")
    @Size(min = 3, max = 30, message = "First name characters should be between 3 and 30")
    private String firstName;
    private String nickName;
    @NotBlank(message = "Invalid: Last name cannot be blank")
    @Size(min = 3, max = 30, message = "Lastname characters should be between 3 and 30")
    private String lastName;
    @NotBlank(message = "Invalid: Password cannot be blank")
    @Size(min = 8, max = 30, message = "Password characters should be between 8 and 30")
    private String password;
    @NotBlank(message = "Invalid: Date cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format should be 'yyyy-MM-dd'")
    private String dateOfBirth;
    @Email
    @NotBlank(message = "Invalid: Email cannot be blank")
    private String email;
    @NotBlank(message = "Invalid: Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10,12}$",
            message = "Phone number must be between 10 to 11 numbers")
    private String phoneNumber;
}
