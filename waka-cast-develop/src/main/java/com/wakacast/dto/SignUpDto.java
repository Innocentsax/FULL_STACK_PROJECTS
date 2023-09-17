package com.wakacast.dto;

import com.wakacast.annotations.PasswordValueMatch;
import com.wakacast.annotations.ValidPassword;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpDto {
    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "[A-Za-z0-9][A-Za-z0-9!.#$%&'*+=?^_`{|}~-]{1,50}@[A_Za-z]+[.][A-Z.a-z]{2,6}")
    private String email;
    private String userPersona;
    @ValidPassword
    private String password;
    @ValidPassword
    private String confirmPassword;

}
