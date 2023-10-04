package com.example.demo.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.Constants;
import com.example.demo.domain.models.Movie;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    public void fillDatabase() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetween_whenPassingNonExistingNameSubstring() {
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("xyz", new Date(0),
                new Date());

        assertTrue(result.isEmpty());

    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetween_whenPassingSubstringThatAllContain() {
        List<Movie> expected = movieRepository.findAll();
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(0),
                new Date());

        assertEquals(expected, result);
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetween_whenPassingLateStartDate() {
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(),
                new Date());

        assertTrue(result.isEmpty());
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetween_whenPassingEarlyEndDate() {
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(0),
                new Date(1L));

        assertTrue(result.isEmpty());

    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetween_whenPassingSomeValidFilterValues() {
        Date startDate = Constants.movies.get(2).getAddingDate();
        Date endDate = new Date();
        String searchString = "Movie 1";
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween(searchString, startDate,
                endDate);

        assertTrue(() -> {
            for (Movie movie : result) {
                if (!movie.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    return false;
                }
                if (movie.getAddingDate().compareTo(startDate) < 0
                        || movie.getAddingDate().compareTo(endDate) > 0) {
                    return false;
                }
            }
            return true;
        });
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingSomeValidFilterValues() {
        Date startDate = Constants.movies.get(2).getAddingDate();
        Date endDate = new Date();
        String searchString = "mov";
        List<String> genreNames = List.of("Thriller");
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetweenAndGenreNameIn(
                searchString, startDate,
                endDate, genreNames);

        assertTrue(() -> {
            for (Movie movie : result) {
                if (!movie.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    return false;
                }
                if (movie.getAddingDate().compareTo(startDate) < 0
                        || movie.getAddingDate().compareTo(endDate) > 0) {
                    return false;
                }
                if (!genreNames.contains(movie.getGenre().getName())) {
                    return false;
                }
            }
            return true;
        });
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingAllGenres() {
        List<Movie> expected = movieRepository.findAll();
        Date startDate = new Date(0);
        Date endDate = new Date();
        String searchString = "mov";
        List<String> genreNames = new ArrayList<>();
        Constants.genres.forEach(genre -> {
            genreNames.add(genre.getName());
        });
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetweenAndGenreNameIn(
                searchString, startDate,
                endDate, genreNames);

        assertEquals(expected, result);
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenEmptyGenres() {
        Date startDate = new Date(0);
        Date endDate = new Date();
        String searchString = "mov";
        List<String> genreNames = new ArrayList<>();
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetweenAndGenreNameIn(
                searchString, startDate,
                endDate, genreNames);

        assertTrue(result.isEmpty());
    }
}
