package com.goCash.controller;
import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.response.FlutterWaveApiResponse;
import com.goCash.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AccountVerificationController {

    private final AccountService accountService;

    @PostMapping("/verify/")
    public ResponseEntity<FlutterWaveApiResponse> verifyAccount(@RequestBody AccountVerificationRequest request) {
        FlutterWaveApiResponse response = accountService.bankVerification(request);
        return ResponseEntity.ok(response);
    }
}
