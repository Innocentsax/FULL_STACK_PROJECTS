package com.example.food.services;

import com.example.food.dto.AdminPasswordResetDto;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.pojos.ApplicationStatisticsResponse;
import com.example.food.pojos.CreateUserResponse;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    BaseResponse adminRequestNewPassword(AdminPasswordResetRequestDto adminPasswordResetRequestDto);

    BaseResponse adminResetPassword(AdminPasswordResetDto adminPasswordResetDto);
    CreateUserResponse displayAdminBasicInformation();
    ApplicationStatisticsResponse showApplicationStatistics();

}
