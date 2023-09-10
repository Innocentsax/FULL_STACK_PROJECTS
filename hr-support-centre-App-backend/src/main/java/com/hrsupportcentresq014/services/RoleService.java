package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.AddPermissionRequest;
import com.hrsupportcentresq014.dtos.request.AddRoleRequest;
import com.hrsupportcentresq014.dtos.response.AddRoleResponse;

public interface RoleService {

    AddRoleResponse addRoles(AddRoleRequest addRole);
    AddRoleResponse addPermission(AddPermissionRequest request, String role_id);
}
