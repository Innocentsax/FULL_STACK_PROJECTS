package com.example.hive.controller;


import com.example.hive.dto.request.BankTransferDto;
import com.example.hive.dto.request.FundWalletRequest;
import com.example.hive.dto.response.*;
import com.example.hive.entity.User;
import com.example.hive.entity.Wallet;
import com.example.hive.exceptions.InsufficientBalanceException;
import com.example.hive.repository.TransactionLogRepository;
import com.example.hive.repository.UserRepository;
import com.example.hive.repository.WalletRepository;
import com.example.hive.service.PayStackService;
import com.example.hive.service.PaymentService;
import com.example.hive.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {
    private final UserRepository userRepository;
    private final PaymentService paymentService;
   private final WalletRepository walletRepository;
    private final PayStackService payStackServic;
    final String DEFAULT_PROVIDER = "paystack";

    private final WalletService walletService;

    @PostMapping("/payment")
    @Operation(summary = "Make Payment to Doer via Paystack Gateway", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponse.class)))})
    public ResponseEntity<AppResponse<PayStackResponse>> taskerFundsWallet(@RequestBody @Valid final FundWalletRequest taskerPaymentRequest, Principal principal) throws Exception {
       // we need to make a call to the paystack api to make the payment and get the response
        PayStackResponse response =  paymentService.initiatePaymentAndSaveToPaymentLog(taskerPaymentRequest, principal);
        return ResponseEntity.ok(AppResponse.buildSuccessTxn(response));
    }

    @PostMapping("/verifyPayment")
    @Operation(summary = "Verify Payment Transaction", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponse.class)))})
    public ResponseEntity<AppResponse<VerifyTransactionResponse>> verifyAndCompleteFunding(@RequestParam String reference, Principal principal) throws Exception {
        // we need to check the status of the payment and complete the transaction
        log.info("verifying- for :: [{}]", reference );
        VerifyTransactionResponse response = paymentService.verifyAndCompletePayment(reference, principal);
        return ResponseEntity.ok(AppResponse.buildSuccessTxn(response));
    }

    @GetMapping("/banks")
    public List<ListBanksResponse> listBanks(@RequestParam(name = "provider", defaultValue = DEFAULT_PROVIDER) String provider) {
        return payStackServic.fetchBanks(provider);
    }

    @PostMapping(path = "/transfer", consumes = "application/json", produces = "application/json")
    public Mono<TransactionResponse> transferFunds(@RequestBody BankTransferDto dto,
                                                   @RequestParam(name = "provider", defaultValue = DEFAULT_PROVIDER) String provider,
                                                   Principal principal) throws InterruptedException {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));

       if (!isAccountBalanceEnough(dto.getAmount(),user)) throw new InsufficientBalanceException("Insufficient funds");
        return payStackServic.transferFunds(dto, provider, user);
    }

    private boolean isAccountBalanceEnough(BigDecimal amount, User user) {
       Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        return wallet.getAccountBalance().compareTo(amount) > 0;
    }


    @GetMapping("/walletBalance")
    public ResponseEntity<AppResponse<WalletResponseDto>> viewDoerWallet(Principal principal) throws Exception {

        WalletResponseDto response = walletService.getWalletByUser(principal);
        return ResponseEntity.ok(AppResponse.buildSuccessTxn(response));
    }

    @GetMapping("/history")
    public ResponseEntity<AppResponse<List<TransactionResponse>>> getTransactionHistory(Principal principal)  {
        List<TransactionResponse> response = walletService.getWalletHistory(principal);
        return ResponseEntity.ok(AppResponse.buildSuccessTxn(response));
    }

}
