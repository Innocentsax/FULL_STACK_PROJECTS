package com.goCash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyCableTvRequest {
    @JsonProperty("billersCode")
    private Long billersCode;

    @JsonProperty("serviceID")
    private String serviceID;
}
