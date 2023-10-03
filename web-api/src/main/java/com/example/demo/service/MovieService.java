package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.dto.MovieDTO;

public interface MovieService {
    List<MovieDTO> getAllMovies();
}
