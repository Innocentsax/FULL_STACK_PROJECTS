package com.decadev.money.way.controller;


import com.decadev.money.way.dto.response.SpendTrendResponse;
import com.decadev.money.way.service.SpendingAndIncomeTrend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequiredArgsConstructor
@RequestMapping("/api/v1/trend")
public class TrendsController {

    private final SpendingAndIncomeTrend spendingAndIncomeTrend;

    @GetMapping("/spending-trend")
    public ResponseEntity<SpendTrendResponse> getSpendingTrend(){
        return new ResponseEntity<>(spendingAndIncomeTrend.getSpendingTrend(), HttpStatus.OK);
    }

}
