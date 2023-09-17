package com.wakacast.repositories;

import com.wakacast.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByGenreTitle(String genreTitle);
}
