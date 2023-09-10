package com.goCash.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class VerifyCableTvResponse {
    private String code;
    private VerifyCableNumberContent content;
}
