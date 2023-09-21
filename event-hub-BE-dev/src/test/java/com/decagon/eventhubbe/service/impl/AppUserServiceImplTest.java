package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.domain.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AppUserServiceImplTest {
    @Mock
    private AppUserServiceImpl appUserService;
    @InjectMocks
    private AppUserRepository appUserRepository;

    @Test
    void uploadProfilePicture() {

    }
}