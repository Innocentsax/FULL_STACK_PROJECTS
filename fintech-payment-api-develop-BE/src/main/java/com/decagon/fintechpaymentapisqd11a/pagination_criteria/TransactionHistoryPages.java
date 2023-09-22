package com.decagon.fintechpaymentapisqd11a.pagination_criteria;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@RequiredArgsConstructor
public class TransactionHistoryPages {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "transactionDate";
}
