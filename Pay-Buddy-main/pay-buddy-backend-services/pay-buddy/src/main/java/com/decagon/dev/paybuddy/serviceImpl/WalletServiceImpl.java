package com.decagon.dev.paybuddy.serviceImpl;

import com.decagon.dev.paybuddy.dtos.requests.CreateTransactionPinDto;
import com.decagon.dev.paybuddy.dtos.requests.WithdrawalDto;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyAirtimeRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyDataPlanRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyElectricityRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.VerifyMerchantRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.*;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.BuyElectricityResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity.VerifyMerchantResponse;
import com.decagon.dev.paybuddy.enums.ResponseCodeEnum;
import com.decagon.dev.paybuddy.enums.TransactionStatus;
import com.decagon.dev.paybuddy.enums.TransactionType;
import com.decagon.dev.paybuddy.exceptions.IncorrectMerchantIdentity;
import com.decagon.dev.paybuddy.exceptions.WalletServiceException;
import com.decagon.dev.paybuddy.models.Transaction;
import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.models.Wallet;
import com.decagon.dev.paybuddy.repositories.TransactionRepository;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import com.decagon.dev.paybuddy.repositories.WalletRepository;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import com.decagon.dev.paybuddy.services.WalletService;
import com.decagon.dev.paybuddy.services.paystack.PayStackWithdrawalService;
import com.decagon.dev.paybuddy.services.paystack.PaystackPaymentService;
import com.decagon.dev.paybuddy.services.paystack.payStackPojos.Bank;
import com.decagon.dev.paybuddy.services.vtpass.VTPassService;
import com.decagon.dev.paybuddy.utilities.ResponseCodeUtil;
import com.decagon.dev.paybuddy.utilities.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final PayStackWithdrawalService payStackWithdrawalService;
    private final PaystackPaymentService paystackPaymentService;

    private final VTPassService vtPassService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final TransactionRepository transactionRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    public User getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication).orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public WalletResponse getWalletBalance() {

        WalletResponse walletResponse;
        try {
            User walletOwner = getLoggedInUser();
            Wallet wallet = walletRepository.findWalletByUser_Email(walletOwner.getEmail());
            walletResponse = WalletResponse.builder()
                    .userName(walletOwner.getFirstName() + " " + walletOwner.getLastName())
                    .walletBalance(wallet.getAccountBalance())
                    .accountNumber(wallet.getAccountNumber())
                    .isPinUpdated(wallet.isPinUpdated())
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.SUCCESS, "Wallet Balance");
        } catch (Exception e) {
            log.error("Email not registered, Wallet balance cannot be displayed: {}", e.getMessage());
            walletResponse = WalletResponse.builder()
                    .walletBalance(null)
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.ERROR);
        }
    }

    @Override
    public ResponseEntity<String> fundWallet(BigDecimal amount, String transactionType) {

        return paystackPaymentService.paystackPayment(amount,transactionType);
    }

    @Override
    public ResponseEntity<String> verifyPayment(String reference, String transactionType) {

        return paystackPaymentService.verifyPayment(reference, transactionType);
    }


    public BaseResponse updateWalletPin (CreateTransactionPinDto createTransactionPinDto) {
        BaseResponse baseResponse = new BaseResponse();
        String authEmail = userUtil.getAuthenticatedUserEmail();

        Wallet userWallet = walletRepository.findWalletByUser_Email(authEmail);
        if (userWallet != null){
            log.info("Database pin: {}", passwordEncoder.encode("0000"));
            log.info("Database pin: {}", userWallet.getPin());
            log.info("User pin: {}", createTransactionPinDto.getOldPin());

            if(passwordEncoder.matches(createTransactionPinDto.getOldPin(), userWallet.getPin())){
                if(createTransactionPinDto.getNewPin().equals(createTransactionPinDto.getConfirmNewPin())){
                    userWallet.setPin(passwordEncoder.encode(createTransactionPinDto.getNewPin()));
                    userWallet.setPinUpdated(true);
                    walletRepository.save(userWallet);
                    return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS,
                            "Wallet pin successfully changed");
                } else{
                    return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR,
                            "New pin does not match the confirmed pin");
                }
            }else {
                return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR,
                        "Old pin does not match existing pin");
            }
        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR,
                "Wallet not found");
    }
    @Override
    public ResponseEntity<List<Bank>> getAllBanks() {

        return payStackWithdrawalService.getAllBanks();
    }

    @Override
    public ResponseEntity<?> walletWithdrawal(WithdrawalDto withdrawalDto) {
        return payStackWithdrawalService.withDrawFromWallet(withdrawalDto);
    }

    @Override
    public ResponseEntity<String> verifyAccountNumber(String accountNumber, String bankCode) {
        return payStackWithdrawalService.verifyAccountNumber(accountNumber, bankCode);
    }

    @Override
    public DataServicesResponse getDataServices() {
        return vtPassService.getDataServices();
    }

    @Override
    public DataPlansResponse getDataPlans(String dataType) {
        return vtPassService.getDataPlans(dataType);
    }

    @Override
    public BuyDataPlanResponse buyDataPlan(BuyDataPlanRequest request, String pin) {
        Wallet wallet = walletRepository.findWalletByUser_Email(getLoggedInUser().getEmail());

        if (!passwordEncoder.matches(pin, wallet.getPin()))  //Check pin accuracy
            throw new WalletServiceException("Pin is wrong");

        if (wallet.getAccountBalance().compareTo(request.getAmount()) <= 0) //Check if wallet balance can perform transaction
            throw new WalletServiceException("Insufficient balance");

        BuyDataPlanResponse response = vtPassService.payDataPlan(request); //Buy data
        if (Objects.equals(response.response_description, "TRANSACTION SUCCESSFUL")) {//UpdateWallet and save Transaction
            wallet.setAccountBalance(wallet.getAccountBalance().subtract(request.getAmount())); //Deduct the wallet
            Wallet updatedWallet = walletRepository.save(wallet);

            Transaction walletTransaction = Transaction.builder()
                    .name(request.getServiceID())
                    .bankCode(request.getPhone())
                    .wallet(updatedWallet)
                    .transactionType(TransactionType.DEBIT)
                    .amount(request.getAmount())
                    .transactionReference(response.requestId)
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .build();
            transactionRepository.save(walletTransaction);
        }

        return response;
    }

    @Override
    public DataServicesResponse getAllElectricityService()
    {

        return vtPassService.getAllElectricityService();
    }

    @Override
    public VerifyMerchantResponse verifyElectricityMeter(VerifyMerchantRequest merchantRequest)
    {
        return vtPassService.verifyElectricityMeter(merchantRequest);
    }

    @Override
    @Transactional
    public BuyElectricityResponse buyElectricity(BuyElectricityRequest electricityRequest,String pin)
    {



        User walletOwner = getLoggedInUser();

        Wallet wallet = walletRepository.findWalletByUser_Email(walletOwner.getEmail());


        if (!passwordEncoder.matches(pin, wallet.getPin()))
            throw new WalletServiceException("Pin is wrong");

        if (wallet.getAccountBalance().compareTo(electricityRequest.getAmount()) <= 0) //Check if wallet balance can perform transaction
            throw new WalletServiceException("Insufficient balance");


        VerifyMerchantRequest merchantRequest = new VerifyMerchantRequest();
        merchantRequest.setServiceID(electricityRequest.getServiceID());
        merchantRequest.setBillersCode(electricityRequest.getBillersCode());
        merchantRequest.setType(electricityRequest.getVariation_code());
        VerifyMerchantResponse verifyMerchantResponse =  verifyElectricityMeter(merchantRequest);


        if(!verifyMerchantResponse.getCode().equals("000"))
        {
            throw new IncorrectMerchantIdentity("incorrect meter number");
        }

        BuyElectricityResponse response = vtPassService.buyElectricity(electricityRequest);
        if (Objects.equals(response.getResponse_description(), "TRANSACTION SUCCESSFUL"))
        {
            wallet.setAccountBalance(wallet.getAccountBalance().subtract(electricityRequest.getAmount())); //Deduct the wallet

            Transaction walletTransaction = Transaction.builder()
                    .name(electricityRequest.getServiceID())
                    .bankCode(electricityRequest.getPhone())
                    .wallet(wallet)
                    .transactionType(TransactionType.DEBIT)
                    .amount(electricityRequest.getAmount())
                    .transactionReference(response.getExchangeReference())
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .build();

        }

        return response;




    }
    public BuyAirtimeResponse buyAirtimeServices(BuyAirtimeRequest buyAirtimeRequest , String pin) {
        Wallet wallet = walletRepository.findWalletByUser_Email(getLoggedInUser().getEmail());


        if (!passwordEncoder.matches(pin, wallet.getPin()))  //Check pin accuracy
            throw new WalletServiceException("Pin is wrong");

        if (wallet.getAccountBalance().compareTo(buyAirtimeRequest.getAmount()) <= 0) //Check if wallet balance can perform transaction
            throw new WalletServiceException("Insufficient balance");

        BuyAirtimeResponse buyAirtimeResponse = vtPassService.buyAirtime(buyAirtimeRequest);

        if (this.isSuccessful(buyAirtimeResponse.getResponse_description())) {
            Wallet updatedWallet = deductCharges(wallet, buyAirtimeRequest.getAmount());//Deduct the wallet

            Transaction walletTransaction = Transaction.builder()
                    .name(buyAirtimeRequest.getServiceID())
                    .bankCode(buyAirtimeRequest.getPhone())
                    .wallet(updatedWallet)
                    .transactionType(TransactionType.DEBIT)
                    .amount(buyAirtimeRequest.getAmount())
                    .transactionReference(buyAirtimeRequest.getRequest_id())
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .build();
            transactionRepository.save(walletTransaction);
        }
        return buyAirtimeResponse;
    }

    @Override
    public AirtimeServiceResponse getAirtimeServices() {
        return vtPassService.getAirtimeServices() ;
    }

    private Wallet deductCharges(Wallet wallet, BigDecimal amount){
        wallet.setAccountBalance(wallet.getAccountBalance().subtract(amount)); //Deduct the wallet
        Wallet updatedWallet = walletRepository.save(wallet);
        return  updatedWallet;
    }
       private  boolean isSuccessful(String response_description){
        if(response_description.equals("TRANSACTION SUCCESSFUL"))
            return true;
        return false;
    }

}
