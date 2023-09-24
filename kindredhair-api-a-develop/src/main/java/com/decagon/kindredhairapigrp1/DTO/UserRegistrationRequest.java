package com.decagon.kindredhairapigrp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    private UserDTO user;
    private AnswerResponseDTO hairProfileAnswer;

}
