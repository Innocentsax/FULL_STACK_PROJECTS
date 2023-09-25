package com.fintech.app.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequest {
    private String email;
    private String bvn;
    private String tx_ref;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String narration;
    private boolean is_permanent;
}
