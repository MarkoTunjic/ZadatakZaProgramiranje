package com.example.demo.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.Constants;
import com.example.demo.domain.dto.MovieDTO;
import com.example.demo.persistance.GenreRepository;
import com.example.demo.persistance.MovieRepository;
import com.example.demo.service.MovieService;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MovieServiceTests {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    private static List<String> genreNames;
    {
        genreNames = new ArrayList<>();
        Constants.genres.forEach(genre -> {
            genreNames.add(genre.getName());
        });
    }

    @BeforeEach
    public void fillDatabase() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
    }

    private static void assertNoDuplicates(List<MovieDTO> movies) {
        Set<Long> ids = new HashSet<>();
        movies.forEach(movie -> {
            ids.add(movie.getId());
        });

        assertEquals(Constants.genres.size(), ids.size());
    }

    @Test
    public void test_getFilteredMovies_whenPassingAllNulls() {
        List<MovieDTO> result = movieService.getFilteredMovies(null, null, null, null);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullMovieName() {
        List<MovieDTO> result = movieService.getFilteredMovies(null, new Date(0), new Date(), genreNames);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingMovieNameThatAllContain() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(0), new Date(), genreNames);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingMovieNameThatNoneContain() {
        List<MovieDTO> result = movieService.getFilteredMovies("xyz", new Date(0), new Date(), genreNames);

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullStartDate() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", null, new Date(), genreNames);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingTooLateStartDate() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(), new Date(), genreNames);

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullEndDate() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(0), null, genreNames);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingTooEarlyEndDate() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(0), new Date(1), genreNames);

        assertTrue(result.isEmpty());
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullGenres() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(0), new Date(), null);

        assertEquals(Constants.genres.size(), result.size());

        assertNoDuplicates(result);
    }

    @Test
    public void test_getFilteredMovies_whenPassingNonExistingGenre() {
        List<MovieDTO> result = movieService.getFilteredMovies("mov", new Date(0), new Date(), List.of("abc"));

        assertTrue(result.isEmpty());
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingSomeValidFilterValues() {
        Date startDate = Constants.movies.get(2).getAddingDate();
        Date endDate = new Date();
        String searchString = "mov";
        List<String> genreNames = List.of("Thriller");
        List<MovieDTO> result = movieService.getFilteredMovies(searchString, startDate, endDate, genreNames);

        assertTrue(() -> {
            for (MovieDTO movie : result) {
                if (!movie.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    return false;
                }
                if (movie.getAddingDate().compareTo(startDate) < 0
                        || movie.getAddingDate().compareTo(endDate) > 0) {
                    return false;
                }
                if (!genreNames.contains(movie.getGenreName())) {
                    return false;
                }
            }
            return true;
        });
    }
}
