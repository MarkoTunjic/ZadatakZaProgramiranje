package com.example.demo.domain.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.domain.dto.GenreDTO;
import com.example.demo.domain.models.Genre;

@Mapper(componentModel = "spring")
public interface GenreDTOGenreMapper {
    GenreDTO genreToGenreDTO(Genre genre);

    List<GenreDTO> genreToGenreDTOs(List<Genre> genre);

}
