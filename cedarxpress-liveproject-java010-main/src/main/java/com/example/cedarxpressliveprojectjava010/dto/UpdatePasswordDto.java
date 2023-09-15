package com.example.cedarxpressliveprojectjava010.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
