package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.TransactionHistoryDto;
import com.decagon.fintechpaymentapisqd11a.pagination_criteria.TransactionHistoryPages;
import org.springframework.data.domain.PageImpl;

public interface TransactionHistory {

    PageImpl<TransactionHistoryDto> allTransaction(TransactionHistoryPages transactionHistoryPages);

}
