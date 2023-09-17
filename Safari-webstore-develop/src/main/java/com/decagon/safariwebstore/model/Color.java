package com.decagon.safariwebstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color extends BaseModel {

    private String color;
//
//    private Long productId;

}
