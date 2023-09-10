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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends BaseEntity{

    private String departmentName;

    @DBRef
    private Employee departmentLead;

    @DBRef
    private List<Employee> listOfEmployee = new ArrayList<>();

    @DBRef
    private List<Team> listOfTeam = new ArrayList<>();

}
