package com.decagon.fitnessoapp.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "author_name", nullable = false)
    private String authorName;

    private String image;

   /* @NotNull
    @Column(nullable = false)*/
    private String contact;

   /* @NotNull
    @Column(nullable = false)*/
    private String biography;
}
