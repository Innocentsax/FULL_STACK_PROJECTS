package com.goCash.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateWalletRequest {
    private String email;
    private boolean is_permanent;
    private String bvn;
    private String tx_ref;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String narration;
    private int amount;

}
