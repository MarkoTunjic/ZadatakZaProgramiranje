package com.example.demo.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Constants;
import com.example.demo.domain.dto.MovieDTO;
import com.example.demo.domain.mappers.MovieDTOMovieMapper;
import com.example.demo.persistance.MovieRepository;
import com.example.demo.service.MovieService;
import com.example.demo.service.impl.MovieServiceImpl;

@SpringBootTest
public class MovieServiceTests {
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Autowired
    private MovieDTOMovieMapper movieMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieRepository, movieMapper);
    }

    @Test
    public void test_getAllMovies() {
        Mockito.when(movieRepository.findAll()).thenReturn(Constants.movies);
        List<MovieDTO> actualEntities = movieService.getAllMovies();

        assertEquals(Constants.movies.size(), actualEntities.size());
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullGenres() {
        String searchString = "mov";
        Date startDate = new Date(0);
        Date endDate = new Date();
        Mockito.when(
                movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetween(searchString, startDate, endDate))
                .thenReturn(Constants.movies);
        List<MovieDTO> actualEntities = movieService.getFilteredMovies(searchString, startDate, endDate, null);

        assertTrue(!actualEntities.isEmpty());
    }

    @Test
    public void test_getFilteredMovies_whenPassingSomeGenres() {
        String searchString = "mov";
        Date startDate = new Date(0);
        Date endDate = new Date();
        List<String> genres = List.of("does not matter");
        Mockito.when(
                movieRepository.findByNameContainingIgnoreCaseAndAddingDateBetweenAndGenreNameIn(searchString,
                        startDate, endDate, genres))
                .thenReturn(Constants.movies);
        List<MovieDTO> actualEntities = movieService.getFilteredMovies(searchString, startDate, endDate, genres);

        assertTrue(!actualEntities.isEmpty());
    }
}
