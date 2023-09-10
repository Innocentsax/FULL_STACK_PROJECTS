package com.goCash.controller;

import com.goCash.dto.request.BankNamesRequest;
import com.goCash.services.BankNamesService;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gocash")
@RequiredArgsConstructor
public class BankNamesController {

    private final BankNamesService bankNamesService;

    @GetMapping("/allbanks")
    public ResponseEntity<ApiResponse<List<BankNamesRequest>>> viewAllBanksInNigeria() {

        return new ResponseEntity<>(new ApiResponse<>("01", "success", bankNamesService.getAllBanks()), HttpStatus.OK);
}
}
