package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 10:11
 * @project SikabethWalletAPI
 * /

/**
 * <a href="https://www.vtpass.com/documentation/mtn-data/">...</a>
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DataPlansResponse {
    public String response_description;
    public Content content;
}
