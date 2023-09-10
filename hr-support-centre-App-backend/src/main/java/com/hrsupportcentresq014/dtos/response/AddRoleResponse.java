package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddRoleResponse {
    private Role role;
}
