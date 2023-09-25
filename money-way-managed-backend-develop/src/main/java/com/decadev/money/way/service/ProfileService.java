package com.decadev.money.way.service;

import com.decadev.money.way.dto.response.ProfileDTO;
import com.decadev.money.way.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    ProfileDTO getProfile(String email);

    String updateProfile(ProfileDTO profileDTO) throws UserNotFoundException;
}
