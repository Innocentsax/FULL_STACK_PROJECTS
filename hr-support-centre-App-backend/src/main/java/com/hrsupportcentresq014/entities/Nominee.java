package com.hrsupportcentresq014.entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Nominee extends BaseEntity{

    private HashMap<String , String> nominators;

    private boolean isApproved;

    @DBRef
    @JsonIncludeProperties(value = {"id", "firstName", "lastName", "email", "department"})
    private Employee nominee;

    @DBRef
    private Award award;

}
