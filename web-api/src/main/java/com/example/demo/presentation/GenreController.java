package com.example.demo.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.dto.GenreDTO;
import com.example.demo.service.GenreService;

@CrossOrigin
@RestController
@RequestMapping("genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }
}
