package com.decagon.controller;


import com.decagon.dto.response.ApiResponse;

import com.decagon.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private final AccountService accountService;
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/get-accountName")
    @Operation(summary = "Get Account Name for a user bank")
    public ResponseEntity<ApiResponse<Object>> accountName(
            @Parameter(description = "Contact information to update", required = true)
            @RequestParam("bankName") String  bankName,
            @RequestParam("accountNumber") String  accountNumber,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<Object> responseDTO = new ApiResponse<>(accountService.getBankCodeAndSend(bankName,accountNumber, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
