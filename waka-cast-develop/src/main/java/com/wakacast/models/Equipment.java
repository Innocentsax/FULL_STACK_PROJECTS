package com.wakacast.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment extends BaseClass {
    private String equipmentName;
    private int quantity;
    private String description;
    private String city;
    private String state;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<EquipmentImage> equipmentImages;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User equipmentOwner;
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private EquipmentType typeOfAsset;
}
