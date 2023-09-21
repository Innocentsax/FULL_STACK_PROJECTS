package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.dto.response.EventResponse;
import com.decagon.eventhubbe.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<String>> uploadProfilePicture(@RequestParam("file") MultipartFile file){
        APIResponse<String> fileUpload = new APIResponse<>(appUserService.uploadProfilePicture(file));
        return new ResponseEntity<>(fileUpload, HttpStatus.OK);
    }


}