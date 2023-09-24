package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name="user_favourites")
public class UserFavorite extends BaseModel {

    @OneToOne
    private Product product;
    @ManyToOne
    private UserDetails userDetails;
}
