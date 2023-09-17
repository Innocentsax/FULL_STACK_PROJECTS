package com.wakacast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    @NotNull(message = "Enter a title for Genre")
    private String genreTitle;
}
