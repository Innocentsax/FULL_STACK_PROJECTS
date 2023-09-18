

package com.example.decapay.pojos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineItemRequestDto {

    @NotBlank(message = "Budget category Id is required")
    private Long budgetCategoryId;

    @NotBlank(message = "Budget Id is required")
    private Long budgetId;

    @NotBlank(message = "the projected amount is required")
    private BigDecimal projectedAmount;

}