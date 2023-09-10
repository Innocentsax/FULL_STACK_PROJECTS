package com.hrsupportcentresq014.services;


import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import org.springframework.stereotype.Service;



public interface UserService {
    AdminResponse register(AdminRequest request);
    boolean isValidDateOfBirth(String dateOfBirth);
}
