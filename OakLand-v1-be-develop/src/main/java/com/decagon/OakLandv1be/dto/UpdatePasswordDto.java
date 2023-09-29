package com.decagon.OakLandv1be.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {



    @NotBlank(message = "oldPassword should not be blank")
     private String oldPassword;

    @NotBlank(message = "newPassword should not be blank")
     private String newPassword;

    @NotBlank(message = "confirmNewPassword should not be blank")
    private String confirmNewPassword;

    }

