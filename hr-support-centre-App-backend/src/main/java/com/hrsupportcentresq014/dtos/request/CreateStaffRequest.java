package com.hrsupportcentresq014.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStaffRequest {
    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Firstname should be more than 2 characters")
    private String firstName;

    @NotBlank(message="Field cannot be empty")
    @Length(min=3, message="Lastname should be more than 2 characters")
    private String lastName;

    @Email(message="Invalid email format")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @NotBlank(message = "Field cannot be empty")
    private String department;

    @NotBlank(message = "Field cannot be empty")
    private String position;

    @NotBlank(message = "Field cannot be empty")
    private String teamManager;

    @NotBlank(message = "Contract type cannot be empty")
    private String contractType;

    @NotBlank(message = "Field cannot be empty")
    private String workLocation;

    @NotNull(message = "Start date cannot be empty")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate startDate;

}
