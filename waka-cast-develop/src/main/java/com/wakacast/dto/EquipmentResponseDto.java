package com.wakacast.dto;

import com.wakacast.models.EquipmentImage;
import com.wakacast.models.EquipmentType;
import com.wakacast.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentResponseDto {
    private Long id;
    private String equipmentName;
    private String typeOfAsset;
    private int quantity;
    private String description;
    private String city;
    private String state;
    private Set<EquipmentImage> equipmentImages;
    private User equipmentOwner;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private EquipmentType equipmentType;
}
