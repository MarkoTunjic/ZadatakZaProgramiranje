package com.example.demo.service.impl;

import java.util.Date;
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

    @Override
    public List<MovieDTO> getFilteredMovies(String movieName, Date startDate, Date endDate, List<String> genreNames) {
        if (movieName == null) {
            movieName = "";
        }
        if (startDate == null) {
            startDate = new Date(0);
        }
        if (endDate == null) {
            endDate = new Date();
        }

        if (genreNames == null) {
            return movieMapper.moviesToMovieDTOs(
                    movieRepository.findByNameContainingAndAddingDateBetween(movieName, startDate, endDate));
        }

        return movieMapper.moviesToMovieDTOs(movieRepository
                .findByNameContainingAndAddingDateBetweenAndGenreNameIn(movieName, startDate, endDate, genreNames));
    }

}
