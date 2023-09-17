package com.wakacast.services.service_impl;

import com.wakacast.dto.RoleDto;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.models.Role;
import com.wakacast.repositories.RoleRepository;
import com.wakacast.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role getRoleById(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findRoleById(roleId);
        return optionalRole.orElse(null);
    }

    @Override
    public String removeRole(Long roleId) {
        try {
            roleRepository.deleteById(roleId);
            return "Role deleted Successfully";
        } catch (Exception ex) {
            throw new ResourceNotFound("Role not found");
        }
    }

    @Override
    public Role getRoleByTitle(String roleTitle) {
        Optional<Role> optionalRole = roleRepository.findRoleByRoleTitle(roleTitle.toUpperCase());
        return optionalRole.orElse(null);
    }

    @Override
    public void addRole(RoleDto role) {
        if(getRoleByTitle(role.getRoleTitle().toUpperCase()) != null)
            return;
        Role roleToAdd = new Role();
        roleToAdd.setRoleTitle(role.getRoleTitle().toUpperCase());

        roleRepository.save(roleToAdd);
    }
}
