package com.hrsupportcentresq014.dtos.request;


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
public class JobPostingRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 50, message = "Title must be at least 3 characters and at most 150 characters")
    private String title;
    @NotBlank(message = "Department Name cannot be blank")
    @Size(min = 3, max = 50, message = "Department name cannot be blank")
    private String departmentName;
    @NotBlank(message = "Invalid: Date cannot be blank")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[01])/\\d{4}$", message = "Incorrect date format. Should be mm/dd/yyyy")
    private String closingDate;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "Job requirements cannot be blank")
    private String requirements;
}
