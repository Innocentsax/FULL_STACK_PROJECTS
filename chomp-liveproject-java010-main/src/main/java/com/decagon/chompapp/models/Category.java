package com.decagon.chompapp.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER, orphanRemoval = true,
    cascade = CascadeType.ALL)

    private List<Product> productList  = new ArrayList<>();



}
