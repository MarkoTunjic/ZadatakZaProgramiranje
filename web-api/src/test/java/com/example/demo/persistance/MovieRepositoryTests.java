package com.example.demo.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.Constants;
import com.example.demo.domain.models.Movie;

@SpringBootTest
@ActiveProfiles("test")
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingNonExistingNameSubstring() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("xyz", new Date(0),
                new Date());

        assertEquals(0, result.size());
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingSubstringThatAllContain() {
        genreRepository.saveAllAndFlush(Constants.genres);
        List<Movie> expected = movieRepository.saveAllAndFlush(Constants.movies);
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(0),
                new Date());

        assertEquals(expected, result);
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingLateStartDate() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(),
                new Date());

        assertEquals(0, result.size());
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingEarlyEndDate() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
        List<Movie> result = movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween("mov", new Date(0),
                new Date(1L));

        assertEquals(0, result.size());
    }

    @Test
    public void integrationTest_findByNameContainingAndAddingDateBetweenAndGenreNameIn_whenPassingSomeValidFilterValues() {
        genreRepository.saveAllAndFlush(Constants.genres);
        movieRepository.saveAllAndFlush(Constants.movies);
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
}
