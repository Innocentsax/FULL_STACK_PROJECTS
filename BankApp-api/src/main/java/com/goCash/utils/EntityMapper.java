package com.goCash.utils;

import com.goCash.dto.request.UserRegistrationRequest;
import com.goCash.dto.response.AirtimePurchaseResponse;
import com.goCash.dto.response.DataSubscriptionResponse;
import com.goCash.dto.response.ElectricityBillResponse;
import com.goCash.dto.response.TvSubscriptionResponse;
import com.goCash.entities.User;
import com.goCash.enums.Roles;
import com.goCash.utils.purchase.PurchaseResponse;
import com.goCash.utils.purchase.Transactions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EntityMapper {
    private final PasswordEncoder passwordEncoder;

    public User dtoToUser(UserRegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .registrationDate(LocalDateTime.now())
                .role(Roles.USER)
                .isVerified(true)
                .build();
    }

    public ElectricityBillResponse newBillResponse(PurchaseResponse purchaseResponse) {

        ElectricityBillResponse response = ElectricityBillResponse.builder().amount(purchaseResponse.getAmount())
                .responseDescription(purchaseResponse.getResponse_description())
                .phone(purchaseResponse.getContent().getTransactions().getPhone())
                .transactionId(purchaseResponse.getContent().getTransactions().getTransactionId())
                .transaction_date(purchaseResponse.getTransaction_date().getDate())
                .purchased_code(purchaseResponse.getPurchased_code())
                .mainToken(purchaseResponse.getMainToken())
                .mainTokenDescription(purchaseResponse.getMainTokenDescription())
                .mainTokenUnits(purchaseResponse.getMainTokenUnits())
                .mainTokenTax(purchaseResponse.getMainTokenTax())
                .mainTokenAmount(purchaseResponse.getMainsTokenAmount())
                .bonusToken(purchaseResponse.getBonusToken())
                .bonusTokenDescription(purchaseResponse.getBonusTokenDescription())
                .bonusTokenUnits(purchaseResponse.getBonusTokenUnits())
                .bonusTokenTax(purchaseResponse.getBonusTokenTax())
                .bonusTokenAmount(purchaseResponse.getBonusTokenAmount())
                .tariffIndex(purchaseResponse.getTariffIndex())
                .debtDescription(purchaseResponse.getDebtDescription())
                .build();

        return response;

    }

    public static DataSubscriptionResponse newPurchaseResponse(PurchaseResponse purchaseResponse) {

        DataSubscriptionResponse response = DataSubscriptionResponse.builder()
                .amount(purchaseResponse.getAmount())
                .responseDescription(purchaseResponse.getResponse_description())
                .phone(purchaseResponse.getContent().getTransactions().getPhone())
                .transactionId(purchaseResponse.getContent().getTransactions().getTransactionId())
                .transactionDate(purchaseResponse.getTransaction_date().getDate())
                .transactionType(purchaseResponse.getContent().getTransactions().getType())
                .operator(purchaseResponse.getContent().getTransactions().getProduct_name())
                .build();

        return response;

    }

    public static TvSubscriptionResponse PurchaseResponse(PurchaseResponse purchaseResponse) {
        Transactions transactions = purchaseResponse.getContent().getTransactions();

        TvSubscriptionResponse.TransactionDate transactionDate = TvSubscriptionResponse.TransactionDate.builder()

                .timezone("Africa/Lagos")
                .build();

        TvSubscriptionResponse response = TvSubscriptionResponse.builder()
                .code(purchaseResponse.getCode())
                .responseDescription(purchaseResponse.getResponse_description())
                .transactionId(transactions.getTransactionId())
                .transactionDate(transactionDate)
                .amount(String.valueOf(transactions.getAmount()))

                .build();

        return response;
    }

    public AirtimePurchaseResponse airtimePurchaseResponse(PurchaseResponse purchaseResponse){

        AirtimePurchaseResponse response = AirtimePurchaseResponse.builder()
                .amount(purchaseResponse.getAmount())
                .responseDescription(purchaseResponse.getResponse_description())
                .phone(purchaseResponse.getContent().getTransactions().getPhone())
                .transactionId(purchaseResponse.getContent().getTransactions().getTransactionId())
                .transactionDate(purchaseResponse.getTransaction_date().getDate())
                .transactionType(purchaseResponse.getContent().getTransactions().getType())
                .Operator(purchaseResponse.getContent().getTransactions().getProduct_name())
                .build();
        return response;
    }
}


