package com.example.food.services;

import com.example.food.dto.AssignRoleRequestDto;
import com.example.food.restartifacts.BaseResponse;

public interface SuperAdminService {
    BaseResponse assignRole(AssignRoleRequestDto assignRoleRequestDto);
}
