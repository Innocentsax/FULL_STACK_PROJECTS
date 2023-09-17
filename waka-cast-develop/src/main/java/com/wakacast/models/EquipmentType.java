package com.wakacast.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EquipmentType extends BaseClass{
    private String equipmentType;
}
