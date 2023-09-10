package com.goCash.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponseDto {

    private String accountNumber;
    private String accountName;
    private String bankName;
}
