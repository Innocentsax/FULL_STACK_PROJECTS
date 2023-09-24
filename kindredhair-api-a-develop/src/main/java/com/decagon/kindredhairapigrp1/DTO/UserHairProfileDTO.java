package com.decagon.kindredhairapigrp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHairProfileDTO {

    @NotEmpty(message = "Question field cannot be empty.")
    private  String question;
    @NotEmpty(message = "Answer field cannot be empty.")
    private  String answer;

}
