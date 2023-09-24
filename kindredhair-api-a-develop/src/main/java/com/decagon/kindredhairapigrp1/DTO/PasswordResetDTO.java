package com.decagon.kindredhairapigrp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {
    private String link;
    private String email;
    private String password;
    private String confirmPassword;
}
