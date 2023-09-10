package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Date issuedAt;
    private Date expiredAt;
    private Employee employee;
}
