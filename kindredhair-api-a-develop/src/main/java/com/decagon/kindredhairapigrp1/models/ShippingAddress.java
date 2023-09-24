package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shipping_address")
public class ShippingAddress  extends BaseModel {

    @NotBlank(message = "ContactAddress field  cannot be empty")
    private  String contactAddress;
    @NotBlank(message = "City field  cannot be empty")
    private  String city;
    @NotBlank(message = "State field  cannot be empty")
    private  String state;
    @NotBlank(message = "Country field  cannot be empty")
    private  String country;
    @NotBlank(message = "zipCode field  cannot be empty")
    private  String zipCode;
    @NotBlank(message = "ContactNumber field  cannot be empty")
    private  String contactNumber;
    @NotBlank(message = "Firstname field  cannot be empty")
    private  String firstname;
    @NotBlank(message = "Lastname field  cannot be empty")
    private  String lastname;
    @ManyToOne
    private  UserDetails userDetails;

}
