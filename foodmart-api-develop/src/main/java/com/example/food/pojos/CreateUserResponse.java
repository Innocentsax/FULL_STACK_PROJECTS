package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserResponse extends BaseResponse {
    private List<UsersBasicInformationDetails> usersBasicInformationDetails;
}
