package com.decagon.borrowerservice.controller;

import com.decagon.borrowerservice.dto.LoanRequestDto;
import com.decagon.borrowerservice.service.BorrowerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/loanApplication")
public class LoanController {
    @Autowired
    private BorrowerService borrowerService;
    @CrossOrigin("http://localhost:5173")
    @PostMapping("/apply")
    public ResponseEntity<LoanRequestDto> loanRequest(@RequestBody LoanRequestDto loanRequestDto, HttpServletRequest request) {
        LoanRequestDto savedLoan = borrowerService.loanRequest(loanRequestDto, request);
        return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
    }
    @CrossOrigin("http://localhost:5173")
    @GetMapping("{id}")
    public ResponseEntity<LoanRequestDto> getLoan(@PathVariable ("id") Long id){
        LoanRequestDto getLoan = borrowerService.getLoan(id);
        return new ResponseEntity<>(getLoan, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @GetMapping()
    public ResponseEntity<List<LoanRequestDto>> getAllLoans(){
        List <LoanRequestDto> getAllLoans = borrowerService.getAllLoans();
        return  ResponseEntity.ok(getAllLoans);
    }

}
