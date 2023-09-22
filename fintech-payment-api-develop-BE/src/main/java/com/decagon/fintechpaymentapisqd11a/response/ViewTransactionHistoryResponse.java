package com.decagon.fintechpaymentapisqd11a.response;
import com.decagon.fintechpaymentapisqd11a.dto.TransactionHistoryDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewTransactionHistoryResponse {

    private Page<TransactionHistoryDto> transactions;
    }

