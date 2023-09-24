package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback extends BaseModel {

    @NotBlank(message = "message field cannot be  empty")
    private String message;

    @NotBlank(message = "subject field cannot be  empty")
    private String subject;

    @ManyToOne
    private Product product;

    @ManyToOne
    private UserDetails userDetails;
}
