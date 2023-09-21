package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.request.AccountRequestDTO;
import com.decagon.eventhubbe.dto.request.RequestAccountDTO;
import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.dto.response.AccountResponse;
import com.decagon.eventhubbe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bank/")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;
    @GetMapping("/getName")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<?>> getAccountName(@RequestParam String bankName,
                                            @RequestParam String accountNumber) {
        System.out.println("jj");
        APIResponse<?> apiResponse = new APIResponse<>(accountService.getBankCodeAndSend(bankName,accountNumber));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/create-account")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<RequestAccountDTO>> saveAccount(@RequestBody RequestAccountDTO requestAccountDTO) {
        APIResponse<RequestAccountDTO> apiResponse = new APIResponse<>(accountService.saveAccount(requestAccountDTO));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/account")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<List<?>>> getAccountByUser(){
        APIResponse<List<?>> apiResponse = new APIResponse<>(accountService.getAccountByLogedInUser());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PutMapping("/updateAccount")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<AccountResponse>> updateAccount(@RequestBody AccountRequestDTO accountRequestDTO){
        APIResponse<AccountResponse> apiResponse =new APIResponse<>(accountService.updateAccount(accountRequestDTO));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{sub_account}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<AccountResponse>> delete(@PathVariable String sub_account){
        APIResponse<AccountResponse> apiResponse = new APIResponse<>(accountService.deleteAccount(sub_account));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
