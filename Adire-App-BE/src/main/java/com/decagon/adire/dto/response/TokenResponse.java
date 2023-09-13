package com.decagon.adire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String token;

    @Override
    public String toString() {
        return
                token;
    }
}
