package com.decadev.money.way.controller;
import com.decadev.money.way.dto.response.ProfileDTO;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.service.ProfileService;
import com.decadev.money.way.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user/profile")
@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService ;
    @GetMapping("/get-profile")
    public ResponseEntity<ProfileDTO> getProfile( ){
        SecurityUtils securityUtils = new SecurityUtils();
        String email= securityUtils.getCurrentUserDetails().getUsername();
        //save the profile data to a database or perform desired action
        return ResponseEntity.ok(profileService.getProfile(email));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody ProfileDTO profileDTO) throws UserNotFoundException {
        return ResponseEntity.ok().body(profileService.updateProfile(profileDTO));
    }
}
