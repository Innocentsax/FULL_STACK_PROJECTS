package com.example.decapay.controllers;
import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.pojos.responseDtos.UpdateProfileResponseDto;
import com.example.decapay.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.io.IOException;

@RestController@Service@RequiredArgsConstructor@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PutMapping("/edit")
    public ResponseEntity<LoginResponseDto> editUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.editUser(userUpdateRequest);
    }

    @PostMapping(value = "/upload-profile-picture" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UpdateProfileResponseDto> uploadProfilePic(@RequestPart(name = "file") MultipartFile image) throws IOException, UserNotFoundException {
        System.out.println("i am inside the controller");
        return userService.uploadProfilePicture(image);
    }
}