package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_hair_profile")
public class UserHairProfile extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    private  UserDetails userDetails;

    @NotBlank(message = "Question field  cannot be empty")
    private  String question;

    @NotBlank(message = "Answer field  cannot be empty")
    private  String answer;


}
