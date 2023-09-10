package com.goCash.dto.response;

import com.goCash.dto.request.BankNamesRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankNamesResponse {
    private String status;
    private String message;
    private List<BankNamesRequest> data;
}
