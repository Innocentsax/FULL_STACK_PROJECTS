package com.wakacast.dto;

import com.wakacast.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
        @ValidPassword
        private String newPassword;
}
