package com.wakacast.dto;

import com.wakacast.models.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRequestedDto {
    @NotEmpty(message = "Cannot be blank")
    private String equipmentName;
    @NotEmpty(message = "Cannot be blank")
    private String typeOfAsset;
    private int quantity;
    private String description;
    private String city;
    private String state;
    @NotEmpty(message = "Cannot be blank")
    private String equipmentType;
}
