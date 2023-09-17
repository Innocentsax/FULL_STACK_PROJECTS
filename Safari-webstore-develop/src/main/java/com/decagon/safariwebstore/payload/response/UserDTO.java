package com.decagon.safariwebstore.payload.response;

import com.decagon.safariwebstore.model.Role;
import com.decagon.safariwebstore.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserDTO {

    private String message;

    private String firstName;

    private String lastName;

    private  String email;

    private String gender;

    private String dateOfBirth;

    private List<Role> roles;

    public static UserDTO build(User user){
        return new UserDTO(
                "Registration Successful",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getRoles()
        );
    }
}
