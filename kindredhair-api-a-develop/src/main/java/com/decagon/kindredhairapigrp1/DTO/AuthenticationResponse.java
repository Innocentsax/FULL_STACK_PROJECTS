package com.decagon.kindredhairapigrp1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is a data carrier. Its used to send authentication data
 * to the front end
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private String role;
    private long id;
}
