package com.decagon.safariwebstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetailsDTO {
        private String firstName;

        private String lastName;

        private  String email;

        private String gender;

        private String dateOfBirth;
}
