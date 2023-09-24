package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DislikedBrand extends BaseModel {

    @NotBlank(message = "Brand Name field cannot be  empty")
    private String brandName;

    @ManyToOne
    private UserDetails userDetails;
}
