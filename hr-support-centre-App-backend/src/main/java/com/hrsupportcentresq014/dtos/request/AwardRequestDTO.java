package com.hrsupportcentresq014.dtos.request;



import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AwardRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;

}
