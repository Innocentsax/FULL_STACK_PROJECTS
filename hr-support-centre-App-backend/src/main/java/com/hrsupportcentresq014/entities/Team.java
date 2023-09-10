package com.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team extends BaseEntity{


    private String name;
    @DBRef
    private Employee teamLeader;

    @DBRef
    private List<Employee> listOfEmployee = new ArrayList<>();
}
