package com.decagon.adire.dto.request;

import com.decagon.adire.enums.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDto {
    private MeasurementType type;

    private double value;
}
