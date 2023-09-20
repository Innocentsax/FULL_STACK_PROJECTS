package com.easyLend.userservice.services;

import com.easyLend.userservice.response.UserResponse;

public interface RabbitMQSender {
    void send(UserResponse userResponse);
}
