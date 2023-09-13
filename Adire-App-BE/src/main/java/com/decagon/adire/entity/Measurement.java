package com.decagon.adire.entity;

import com.decagon.adire.enums.MeasurementType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Measurement extends BaseEntity{
    @Column(name = "measurements")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;


    private double value;



}
