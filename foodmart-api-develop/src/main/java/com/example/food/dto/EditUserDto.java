package com.example.food.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EditUserDto {

    @NotBlank(message = "FirstName must not be blank!!")
    private String firstName;

    @NotBlank(message = "LastName must not be blank!!")
    private String lastName;

    @NotNull(message = "DOB must not be blank!!")
    private Date dateOfBirth;

    @Email(message = "Email is required !!")
    private String email;
}
