package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.GenreDTO;
import com.example.demo.domain.mappers.GenreDTOGenreMapper;
import com.example.demo.persistance.GenreRepository;
import com.example.demo.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreDTOGenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreDTOGenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreMapper.genreToGenreDTOs(genreRepository.findAll());
    }

}