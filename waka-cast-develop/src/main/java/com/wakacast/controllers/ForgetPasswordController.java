package com.wakacast.controllers;

import com.wakacast.dto.ResetPasswordDto;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.ForgetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.wakacast.controllers.UserController.response;

@RestController
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final ForgetPasswordService forgetPasswordService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/generateTokenAndMail")
    public ResponseEntity<ResponseData> generateResetToken(@RequestParam String email) throws IOException {
        forgetPasswordService.generateResetToken(email);
        return  response(HttpStatus.OK,"Token has been generated successfully");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<ResponseData> resetPassword(@PathVariable String token,
                                                      @Valid @RequestBody ResetPasswordDto resetPasswordDto
    )
    {
        forgetPasswordService.resetPassword(resetPasswordDto, token);
        return response(HttpStatus.OK, "Password reset successfully");
    }
}
