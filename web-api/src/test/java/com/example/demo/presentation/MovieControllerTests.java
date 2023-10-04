package com.example.demo.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.Constants;
import com.example.demo.persistance.GenreRepository;
import com.example.demo.persistance.MovieRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class MovieControllerTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MockMvc mockMvc;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

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

    private void performMockMVCAndCheckIfAllElementsArePresent(String movieName, Date startDate, Date endDate,
            List<String> genreNames) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/filtered")
                .param("movieName", movieName)
                .param("startDate", startDate != null ? sdf.format(startDate) : null)
                .param("endDate", endDate != null ? sdf.format(endDate) : null)
                .param("genreNames", genreNames != null ? String.join(",", genreNames) : null)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(Constants.movies.size())));
        System.out.println("a");
    }

    private void performMockMVCAndCheckIfEmpty(String movieName, Date startDate, Date endDate,
            List<String> genreNames) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/filtered")
                .param("movieName", movieName)
                .param("startDate", startDate != null ? sdf.format(startDate) : null)
                .param("endDate", endDate != null ? sdf.format(endDate) : null)
                .param("genreNames", genreNames != null ? String.join(",", genreNames) : null)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void test_getFilteredMovies_whenPassingAllNulls() throws Exception {
        performMockMVCAndCheckIfAllElementsArePresent(null, null, null, null);
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullMovieName() throws Exception {
        System.out.println(new Date().after(Constants.movies.get(9).getAddingDate()));
        performMockMVCAndCheckIfAllElementsArePresent(null, new Date(0), null, genreNames);
    }

    @Test
    public void test_getFilteredMovies_whenPassingMovieNameThatAllContain() throws Exception {
        performMockMVCAndCheckIfAllElementsArePresent("mov", new Date(0), null, genreNames);
    }

    @Test
    public void test_getFilteredMovies_whenPassingMovieNameThatNoneContain() throws Exception {
        performMockMVCAndCheckIfEmpty("xyz", new Date(0), null, genreNames);
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullStartDate() throws Exception {
        performMockMVCAndCheckIfAllElementsArePresent("mov", null, null, genreNames);

    }

    @Test
    public void test_getFilteredMovies_whenPassingTooLateStartDate() throws Exception {
        performMockMVCAndCheckIfEmpty("mov", new Date(), new Date(), genreNames);

    }

    @Test
    public void test_getFilteredMovies_whenPassingNullEndDate() throws Exception {
        performMockMVCAndCheckIfAllElementsArePresent("mov", new Date(0), null, genreNames);
    }

    @Test
    public void test_getFilteredMovies_whenPassingTooEarlyEndDate() throws Exception {
        performMockMVCAndCheckIfEmpty("mov", new Date(0), new Date(1), genreNames);
    }

    @Test
    public void test_getFilteredMovies_whenPassingNullGenres() throws Exception {
        performMockMVCAndCheckIfAllElementsArePresent("mov", new Date(0), null, null);

    }

    @Test
    public void test_getFilteredMovies_whenPassingNonExistingGenre() throws Exception {
        performMockMVCAndCheckIfEmpty("mov", new Date(0), null, List.of("abc"));
    }
}
