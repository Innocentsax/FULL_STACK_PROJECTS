package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
