package com.example.food.controllers;

import com.example.food.dto.AssignRoleRequestDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.serviceImpl.SuperAdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super-admin")
@AllArgsConstructor
public class SuperAdminController {
    private SuperAdminServiceImpl superAdminService;
    @PutMapping("/assign-role")
    public BaseResponse assignRole(@RequestBody AssignRoleRequestDto assignRoleRequestDto){
        return superAdminService.assignRole(assignRoleRequestDto);
    }
}
