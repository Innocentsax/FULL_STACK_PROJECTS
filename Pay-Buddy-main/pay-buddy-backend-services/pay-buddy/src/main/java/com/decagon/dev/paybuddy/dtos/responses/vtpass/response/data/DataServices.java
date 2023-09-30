package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 20:03
 * @project Pay-Buddy
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataServices {
    private String serviceID;
    private String name;
    private String image;
}
