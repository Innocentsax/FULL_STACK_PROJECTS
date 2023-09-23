package com.decagon.fitnessoapp.dto;

import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PersonRequest {

    @NotEmpty(message = "Username Name cannot be empty")
    @Size(min = 2, message = "username must not be less than 1")
    private String userName;

    @NotEmpty(message = "first Name cannot be empty")
    @Size(min = 2, message = "first Name must not be less than 2")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be empty")
    @Size(min = 2, message = "last Name must not be less than 2")
    private String lastName;

    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "password must not be less than 8")
    private String password;

    @Size(min = 10, max = 14, message = "invalid Phone Number")
    private String phoneNumber;

    private String gender;

    @ApiModelProperty(hidden = true)
    private ROLE_DETAIL roleDetail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    private String image;
}
