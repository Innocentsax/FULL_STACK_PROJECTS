package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.AddPermissionRequest;
import com.hrsupportcentresq014.dtos.request.AddRoleRequest;
import com.hrsupportcentresq014.dtos.response.AddRoleResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Permission;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.security_config.utils.SecurityUtils;
import com.hrsupportcentresq014.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final SecurityUtils securityUtils;
    private final EmployeeRepository employeeRepository;

    String EXCEPTION_RESPONSE = "Logged in User doesn't have the required authorities to perform function";
    @Override
    public AddRoleResponse addRoles(AddRoleRequest addRole) {
        if(getEmployeeRole()) {
            Role role = Role.builder()
                    .id(addRole.getId())
                    .name(addRole.getName().toUpperCase())
                    .permissions(new ArrayList<>())
                    .build();
            Role roles = roleRepository.save(role);
            return AddRoleResponse.builder()
                    .role(roles)
                    .build();
        }
            throw new RuntimeException(EXCEPTION_RESPONSE);
    }


    //Helper method to get Employee
    private Employee getEmployee(String email){
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }

    //Helper method to get the role from employee
    private boolean getEmployeeRole(){
        UserDetails userDetails = securityUtils.getCurrentUserDetails();
        Employee employee = getEmployee(userDetails.getUsername());
      Role role = employee.getRole();
      return role.getName().equalsIgnoreCase("ADMIN");
    }
    @Override
    public AddRoleResponse addPermission(AddPermissionRequest request, String role_id){
        if(getEmployeeRole()) {
            Role role = roleRepository.findRoleById(role_id)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Permission permission = new Permission();
            permission.setPermission(request.getPermission().toUpperCase());
            role.getPermissions().add(permission);
            Role roles = roleRepository.save(role);
            return AddRoleResponse.builder()
                    .role(roles)
                    .build();
        }
        throw new RuntimeException("Logged in User doesn't have the required authorities to perform function");
    }

}
