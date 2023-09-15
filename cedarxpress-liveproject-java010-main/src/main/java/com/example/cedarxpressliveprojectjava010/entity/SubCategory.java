package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SubCategory extends Base{
    @Column(unique = true)
    private String subCategoryName;

    @ManyToOne
    private Category category;


}
