package com.decagon.dev.paybuddy.Controllers;

import com.decagon.dev.paybuddy.dtos.requests.CreateTransactionPinDto;
import com.decagon.dev.paybuddy.dtos.requests.WithdrawalDto;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyAirtimeRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyDataPlanRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyElectricityRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.VerifyMerchantRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.*;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.BuyElectricityResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.VerifyMerchantResponse;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import com.decagon.dev.paybuddy.services.WalletService;
import com.decagon.dev.paybuddy.services.paystack.payStackPojos.Bank;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;
    @PutMapping("/updateWalletPin")
    public BaseResponse updateWalletPin(@RequestBody CreateTransactionPinDto createTransactionPinDto){
        return walletService.updateWalletPin(createTransactionPinDto);
    }

    @GetMapping("/balance")
    public ResponseEntity<WalletResponse> getBalance() {
        WalletResponse response = walletService.getWalletBalance();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/fundWallet")
    public ResponseEntity<String> fundWallet(@RequestParam BigDecimal amount){
        return walletService.fundWallet(amount, "fundwallet");
    }

    @GetMapping("/verifyPayment/{reference}/{paymentMethod}")
    public ResponseEntity<String> verifyPayment(@PathVariable String reference, @PathVariable  String paymentMethod){
        return walletService.verifyPayment(reference,paymentMethod);
    }
    @PostMapping("/getBankDetails")
    public ResponseEntity<List<Bank>> fetchBankDetails(){

        return walletService.getAllBanks();
    }
    @PostMapping("/sendMoney")
    public ResponseEntity<?> walletWithdrawal(@RequestBody WithdrawalDto withdrawalDto){
        return walletService.walletWithdrawal(withdrawalDto);
    }
    @PostMapping("/verifyAccountNumber")
    public ResponseEntity<String> verifyAccountNumber(@RequestParam String accountNumber, @RequestParam String bankCode){
        return walletService.verifyAccountNumber(accountNumber, bankCode);
    }

    @GetMapping("/data-services")
    public ResponseEntity<DataServicesResponse> getDataServices() {
        return ResponseEntity.ok(walletService.getDataServices());
    }

    @GetMapping("/data-services/{dataType}")
    public ResponseEntity<DataPlansResponse> getDataSubscriptions(
            @Parameter(description = "glo-data, mtn-data, etisalat-data, airtel-data")
            @PathVariable String dataType
    ) {
        return ResponseEntity.ok(walletService.getDataPlans(dataType));
    }

    @PostMapping("/buy-data-plan")
    public ResponseEntity<BuyDataPlanResponse> buyDataPlan(@RequestBody BuyDataPlanRequest request,
                                                           @RequestParam String pin) {
        return ResponseEntity.ok(walletService.buyDataPlan(request, pin));
    }


    @GetMapping("/electricity-services")
    public ResponseEntity<DataServicesResponse> getAllElectricityServices(){
        return ResponseEntity.ok(walletService.getAllElectricityService());
    }
    @PostMapping("/buy-electricity")
    public ResponseEntity<BuyElectricityResponse> buyElectricity(@RequestBody BuyElectricityRequest request, @RequestParam String pin)
    {
        return ResponseEntity.ok(walletService.buyElectricity(request, pin));
    }

    @PostMapping("/verify-merchant")
    public ResponseEntity<VerifyMerchantResponse> verifyMerchant(@RequestBody VerifyMerchantRequest request)
    {
        return ResponseEntity.ok(walletService.verifyElectricityMeter(request));
    }

    @PostMapping("/buy-airtime")
    public ResponseEntity<BuyAirtimeResponse> buyAirtime(@RequestBody BuyAirtimeRequest buyAirtimeRequest, @RequestParam String pin) {
        BuyAirtimeResponse response = walletService.buyAirtimeServices(buyAirtimeRequest,pin);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/airtime-services")
    public ResponseEntity<AirtimeServiceResponse> AirtimeServices() {
        AirtimeServiceResponse response = walletService.getAirtimeServices();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
