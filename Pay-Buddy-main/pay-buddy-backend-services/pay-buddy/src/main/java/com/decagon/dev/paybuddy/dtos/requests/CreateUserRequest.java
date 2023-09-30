package com.decagon.dev.paybuddy.dtos.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;


/**
 * A DTO for the {@link com.decagon.dev.paybuddy.models.User} entity
 */
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest implements Serializable {
    @NotBlank(message = "First name cannot be blank")
    private  String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private  String lastName;
    private  String otherName;
    @NotBlank(message = "Email name cannot be blank")
    private  String email;
    @Email(message = "Please enter a valid email address")
    private  String phoneNumber;
    @NotBlank(message = "Password cannot be blank")
    private  String bvn;
    @NotBlank(message = "Confirm password name cannot be blank")
    private  String password;

}
