package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.AssignRoleRequestDto;
import com.example.food.model.Users;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.SuperAdminService;
import com.example.food.util.ResponseCodeUtil;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class SuperAdminServiceImpl implements SuperAdminService {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    @Override
    public BaseResponse assignRole(AssignRoleRequestDto assignRoleRequestDto) {
        BaseResponse response = new BaseResponse();
        Optional<Users> registeredUser = userRepository.findByEmail(assignRoleRequestDto.getEmail());
        if(registeredUser.isPresent() && registeredUser.get().getIsActive() && registeredUser.get().getConfirmationToken() == null){
            registeredUser.get().setRole(assignRoleRequestDto.getRole());
            userRepository.save(registeredUser.get());
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS,
                    "Dear " + registeredUser.get().getFirstName() + " you are now a/an " + registeredUser.get().getRole());
        }
        return responseCodeUtil.updateResponseData(response,ResponseCodeEnum.ERROR, "User not found");
    }
}
