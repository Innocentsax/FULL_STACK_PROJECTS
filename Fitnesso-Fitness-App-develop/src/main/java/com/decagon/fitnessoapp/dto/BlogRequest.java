package com.decagon.fitnessoapp.dto;

import lombok.Data;

@Data
public class BlogRequest {
    private String blogTitle;
    private String blogContent;
    private Long authorId;
}
