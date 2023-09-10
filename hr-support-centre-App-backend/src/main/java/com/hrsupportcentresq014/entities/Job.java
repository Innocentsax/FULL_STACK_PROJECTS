package com.hrsupportcentresq014.entities;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Job extends BaseEntity{

    private String title;
    private String description;
    private String requirements;
    private String departmentName;
    private String closingDate;
    private Boolean isActive;
    private Integer totalNumberOfApplicants;
    @DBRef
    private List<Employee> listOfApplicant = new ArrayList<>();
}
