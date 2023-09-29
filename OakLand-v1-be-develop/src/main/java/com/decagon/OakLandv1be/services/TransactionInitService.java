package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.TransactionDto;
import com.decagon.OakLandv1be.dto.TransactionInitResponseDto;
import com.decagon.OakLandv1be.entities.Amount;
import org.json.JSONObject;
import org.springframework.data.domain.Page;

public interface TransactionInitService {


    JSONObject initTransaction(Amount amount) throws Exception;

    TransactionInitResponseDto verifyPayment(String reference);

    String finalizeTransaction(String reference);

     Page<TransactionDto> viewAllTransactionsPaginated(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
}
