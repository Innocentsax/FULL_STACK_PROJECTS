package com.example.money_way.service.impl;

import com.example.money_way.dto.request.TransactionLogRequest;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.FinancialSummaryResponseDto;
import com.example.money_way.dto.response.TransactionLogResponse;
import com.example.money_way.model.Transaction;
import com.example.money_way.model.User;
import com.example.money_way.repository.TransactionRepository;
import com.example.money_way.service.TransactionService;
import com.example.money_way.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AppUtil appUtil;

    @Override
    public ApiResponse<List<TransactionLogResponse>> viewTransactionLog(TransactionLogRequest request) {
        User user = appUtil.getLoggedInUser();
        Long userId = user.getId();

        String endDate = Objects.isNull(request.getEndDate()) ? LocalDateTime.now().toString() :
                LocalDateTime.of(LocalDate.parse(request.getEndDate()), LocalTime.MIDNIGHT).toString();

        String startDate = Objects.isNull(request.getStartDate()) ? LocalDateTime.parse(endDate)
                .minus(Period.ofYears(2)).toString() : LocalDateTime.of(LocalDate.parse(request.getStartDate()),
                LocalTime.MIDNIGHT).toString();

        List<Transaction> transactionList = transactionRepository.findAllByUserId(userId, request.getPageSize(),
                request.getPageNo(), startDate, endDate);

        List<TransactionLogResponse> transactionLogResponseList = new ArrayList<>();
        for (Transaction transaction : transactionList){
            mapToResponse(transaction, transactionLogResponseList);
        }
        return ApiResponse.<List<TransactionLogResponse>>builder()
                .data(transactionLogResponseList)
                .build();
    }

    private static void mapToResponse(Transaction transaction,
                                      List<TransactionLogResponse> transactionLogResponseList) {
        TransactionLogResponse transactionLogResponse = TransactionLogResponse.builder()
                .accountName(transaction.getAccountName())
                .description(transaction.getDescription())
                .currency(transaction.getCurrency())
                .amount(transaction.getAmount())
                .status(transaction.getStatus().name())
                .paymentType(transaction.getPaymentType())
                .responseMessage(transaction.getResponseMessage())
                .providerStatus(transaction.getProviderStatus())
                .date(transaction.getCreatedAt())
                .requestId(transaction.getRequestId())
                .virtualAccountRef(transaction.getVirtualAccountRef())
                .build();
        transactionLogResponseList.add(transactionLogResponse);
    }


    public List<FinancialSummaryResponseDto> getTransactionGraphByMonth() {

        User user = appUtil.getLoggedInUser();
        Long userId = user.getId();

        return transactionRepository.getTransactionsByMonth(userId);
    }


}