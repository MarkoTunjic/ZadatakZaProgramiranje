package com.example.demo;

import java.util.Date;
import java.util.List;

import com.example.demo.domain.models.Genre;
import com.example.demo.domain.models.Movie;

public class Constants {
    public static List<Genre> genres = List.of(
            new Genre("Action"),
            new Genre("Comedy"),
            new Genre("Drama"),
            new Genre("Horror"),
            new Genre("Sci-Fi"),
            new Genre("Adventure"),
            new Genre("Romance"),
            new Genre("Fantasy"),
            new Genre("Mystery"),
            new Genre("Thriller"));
    public static List<Movie> movies = List.of(new Movie("Movie 1", new Date(1577836800000L), genres.get(0)),
            new Movie("Movie 2", new Date(1609459200000L), genres.get(1)),
            new Movie("Movie 3", new Date(1640995200000L), genres.get(2)),
            new Movie("Movie 4", new Date(1672531200000L), genres.get(3)),
            new Movie("Movie 5", new Date(1577836800000L), genres.get(4)),
            new Movie("Movie 6", new Date(1609459200000L), genres.get(5)),
            new Movie("Movie 7", new Date(1640995200000L), genres.get(6)),
            new Movie("Movie 8", new Date(1672531200000L), genres.get(7)),
            new Movie("Movie 9", new Date(1577836800000L), genres.get(8)),
            new Movie("Movie 10", new Date(), genres.get(9)));
}
