package com.wakacast.repositories;

import com.wakacast.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {
    private final GenreRepository genreRepository;

    private Genre genre;

    @Autowired
    public GenreRepositoryTest(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setGenreTitle("Movie Production");
    }

    @Test
    void saveGenre() {
        Genre savedGenre = genreRepository.save(genre);
        assertThat(savedGenre)
                .isNotNull()
                .isEqualTo(genre);
    }

    @Test
    void findGenreByGenreTitle() {
        genreRepository.save(genre);
        Genre returnedGenre = genreRepository.findGenreByGenreTitle("Movie Production").get();
        assertThat(returnedGenre).isNotNull().isEqualTo(genre);
    }
}