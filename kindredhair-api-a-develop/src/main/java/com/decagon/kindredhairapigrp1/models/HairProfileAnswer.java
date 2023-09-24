package com.decagon.kindredhairapigrp1.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="hair_profile_answer")
public class HairProfileAnswer extends BaseModel  {

    @NotBlank(message = "Answer cannot be empty")
    private String content;

    @ManyToOne
    @JoinColumn(name = "hair_profile_question_id")
    private HairProfileQuestion hairProfileQuestion;

}