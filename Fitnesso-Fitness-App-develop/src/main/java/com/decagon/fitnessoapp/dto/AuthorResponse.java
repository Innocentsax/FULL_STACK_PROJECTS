package com.decagon.fitnessoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorResponse {
    private String authorName;
    private String image;
    private String contact;
    private String biography;

}
