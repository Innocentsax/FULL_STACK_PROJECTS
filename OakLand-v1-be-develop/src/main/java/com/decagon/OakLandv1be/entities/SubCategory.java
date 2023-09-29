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
@ToString
@Builder
@Table(name = "sub_category_tbl")
public class SubCategory extends BaseEntity{

    private String name;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "subCategory", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Product> products;


}
