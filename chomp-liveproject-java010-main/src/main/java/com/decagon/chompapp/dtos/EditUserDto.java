package com.decagon.chompapp.dtos;

import com.decagon.chompapp.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String firstName;

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String lastName;

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;


}
