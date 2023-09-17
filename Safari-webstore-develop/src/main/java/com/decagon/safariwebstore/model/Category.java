package com.decagon.safariwebstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseModel{

    private String name;
    //should have otm for email
}
