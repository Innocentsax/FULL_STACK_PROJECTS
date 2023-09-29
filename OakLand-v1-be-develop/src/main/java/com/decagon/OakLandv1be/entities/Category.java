package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "category_tbl")
public class Category extends BaseEntity{

    private String name;
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SubCategory> subCategories;
}
