package com.example.demo.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.dto.MovieDTO;
import com.example.demo.service.MovieService;

@CrossOrigin
@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public ResponseEntity<List<MovieDTO>> getAllRounds() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
}
