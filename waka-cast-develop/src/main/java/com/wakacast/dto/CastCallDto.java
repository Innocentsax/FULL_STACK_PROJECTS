package com.wakacast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.wakacast.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CastCallDto {

    private Long id;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String talentSkill;
    @NotNull
    private int minAge;
    @NotNull
    private int maxAge;
    @NotNull
    private Gender gender;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate postExpiryDate;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String projectName;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String projectDescription;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String projectType;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String languages;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String otherRequirements;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String country;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String state;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String city;
    @NotNull
    @NotBlank(message = "This is a required field")
    private String postalZipCode;

}
