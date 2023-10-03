package com.example.demo.service;

import java.util.Date;
import java.util.List;

import com.example.demo.domain.dto.MovieDTO;

public interface MovieService {
    List<MovieDTO> getAllMovies();

    List<MovieDTO> getFilteredMovies(String movieName, Date startDate, Date endDate, List<String> genreNames);
}
