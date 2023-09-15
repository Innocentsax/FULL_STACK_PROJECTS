package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequest {
    @NotBlank(message = "email is required!")
    private String email;
}
