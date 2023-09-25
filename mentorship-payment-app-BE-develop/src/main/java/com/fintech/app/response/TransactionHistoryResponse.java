package com.fintech.app.response;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryResponse {

    private List<TransactionHistoryDto> content;
    private Pageable page;
    private int currentPage;
    private int numberOfElements;
    private int totalPages;
    private Long totalElements;
    private boolean first;
    private boolean last;

}
