package com.wakacast.services;

import com.wakacast.dto.RoleDto;
import com.wakacast.models.Role;

public interface RoleService {

    Role getRoleById(Long roleId);

    String removeRole(Long roleId);

    Role getRoleByTitle(String roleName);

    void addRole(RoleDto role);
}
