package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 20:02
 * @project Pay-Buddy
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataServicesResponse {
    private String response_description;
    public List<DataServices> content;
}
