package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="hair_profile_question")
public class HairProfileQuestion extends BaseModel{

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Question cannot be empty")
    private String content;
  
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hairProfileQuestion" )
    private Set<HairProfileAnswer> answers;

}
