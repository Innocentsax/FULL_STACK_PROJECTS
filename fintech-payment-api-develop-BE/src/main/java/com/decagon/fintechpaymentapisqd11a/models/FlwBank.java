package com.decagon.fintechpaymentapisqd11a.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlwBank {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;
}
