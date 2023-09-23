package com.decagon.fitnessoapp.dto;

import lombok.Data;

@Data
public class BlogPostResponse {
    private Long id;
    private String title;
    private String content;
    private String contact;
    private String biography;
    private String authorName;
    private String Image;
}
