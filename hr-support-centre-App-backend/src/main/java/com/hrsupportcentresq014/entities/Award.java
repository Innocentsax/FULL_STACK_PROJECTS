package com.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "awards")
@Builder
public class Award extends BaseEntity {

    private String title;

    private String description;

    private int year;

    private LocalDate date;

    @DBRef
    private List<Nominee> unapprovedNominees = new ArrayList<>();

    @DBRef
    private List<Nominee> approvedNominees = new ArrayList<>();

    @DBRef
    private Nominee recipient;

}