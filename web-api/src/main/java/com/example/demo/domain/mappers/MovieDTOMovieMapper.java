package com.example.demo.domain.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.domain.dto.MovieDTO;
import com.example.demo.domain.models.Movie;

@Mapper(componentModel = "spring")
public interface MovieDTOMovieMapper {
    @Mapping(target = "genreName", source = "genre.name")
    MovieDTO movieToMovieDTO(Movie movie);

    List<MovieDTO> moviesToMovieDTOs(List<Movie> movies);
}
