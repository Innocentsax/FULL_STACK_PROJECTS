package com.decagon.fitnessoapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
public class BlogContext {
    @NotEmpty(message = "Title cannot be blank")
    @Size(min = 2, message = "Title must not be less than 1")
    private String title;

    @NotEmpty(message = "Content cannot be blank")
    @Size(min = 10, message = "username must not be less than 1")
    private String content;

    private String authorName;

    private String image;

    private String contact;

    private String biography;
}
