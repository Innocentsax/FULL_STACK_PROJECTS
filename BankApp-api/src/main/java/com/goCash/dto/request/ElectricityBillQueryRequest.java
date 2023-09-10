package com.goCash.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ElectricityBillQueryRequest {
    private Long billersCode;
    private String serviceID;
    private String type;
}
