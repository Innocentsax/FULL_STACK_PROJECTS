package com.decadev.money.way.dto.response;

import com.decadev.money.way.enums.Status;
import com.decadev.money.way.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionHistoryResponse {
    private Long id;

    private String amount;

    private LocalDateTime date;

    private String type;

    private String beneficiary;

    private String description;

    private String referenceId;

    private Status status;
    @JsonIgnore
    private User user;


}
