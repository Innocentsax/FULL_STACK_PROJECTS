package com.example.food.dto;

import com.example.food.Enum.Role;
import lombok.Data;

@Data
public class AssignRoleRequestDto {
    private String email;
    private Role role;
}
