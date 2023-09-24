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
@Table(name="contact_us")
public class ContactUs extends BaseModel {

    @NotBlank(message = "message field cannot be  empty")
    private  String message;

    @NotBlank(message = "email field cannot be  empty")
    private  String email;
    
    @NotBlank(message = "subject field cannot be  empty")
    private  String subject;

}
