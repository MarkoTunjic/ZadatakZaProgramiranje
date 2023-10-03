package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.MovieDTO;
import com.example.demo.domain.mappers.MovieDTOMovieMapper;
import com.example.demo.persistance.MovieRepository;
import com.example.demo.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieDTOMovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieDTOMovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieMapper.moviesToMovieDTOs(movieRepository.findAll());
    }

}
