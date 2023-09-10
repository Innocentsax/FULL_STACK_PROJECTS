package com.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant extends BaseEntity{


    private String employeeFirstName;

    private String applicationLetterUrl;


}
