package com.wakacast.services.service_impl;

import com.wakacast.dto.RoleDto;
import com.wakacast.dto.RoleUserDto;
import com.wakacast.dto.SignUpDto;
import com.wakacast.services.RoleService;
import com.wakacast.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperAdmin implements CommandLineRunner {
    private final UserServices userServices;
    @Value("${super.admin.email}")
    private String email;
    @Value("${super.admin.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        userServices.addSuperAdmin(new SignUpDto(email,
                "PRODUCER", password, password ));
    }
}
