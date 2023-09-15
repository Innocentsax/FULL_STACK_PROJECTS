package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Category extends Base{
    @Column(unique = true)
    private String categoryName;
}