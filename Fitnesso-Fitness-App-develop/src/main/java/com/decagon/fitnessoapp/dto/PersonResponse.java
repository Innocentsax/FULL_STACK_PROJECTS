package com.decagon.fitnessoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
