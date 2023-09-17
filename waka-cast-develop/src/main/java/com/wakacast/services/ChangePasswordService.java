package com.wakacast.services;

import com.wakacast.dto.ChangePasswordDto;


public interface ChangePasswordService {
    String updatePassword(ChangePasswordDto changePasswordDto, String email);
}
