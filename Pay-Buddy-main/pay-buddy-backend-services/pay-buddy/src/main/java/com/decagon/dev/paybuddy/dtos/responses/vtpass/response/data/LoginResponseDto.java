package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;


import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponseDto extends BaseResponse {
    private String firstName;
    private String lastName;
    private String email;
    private int loginCount;
    private String token;
}
