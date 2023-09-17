package com.wakacast.controllers;

import com.wakacast.dto.AdminDto;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.wakacast.controllers.UserController.response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/super-admin")
public class SuperAdminController {
    private final AdminService adminService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create-admin")
    public ResponseEntity<ResponseData> createAdmin(@Valid @RequestBody AdminDto adminDto) throws IOException {
        return response(HttpStatus.CREATED, adminService.createNewAdmin(adminDto));
    }
}
