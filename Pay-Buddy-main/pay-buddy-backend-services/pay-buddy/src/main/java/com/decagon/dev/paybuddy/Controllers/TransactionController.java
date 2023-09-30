package com.decagon.dev.paybuddy.Controllers;


import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.TransactionResponseViewModel;
import com.decagon.dev.paybuddy.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/viewTransaction")
    public ResponseEntity<TransactionResponseViewModel> viewTransaction(
            @RequestParam(name = "page", defaultValue = "0")
            int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(transactionService.viewWalletTransaction(page, limit));
    }

}
