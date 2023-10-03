package com.example.demo.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.models.Movie;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNameContaining(String name);

    List<Movie> findByAddingDateBetween(Date start, Date end);

    List<Movie> findByGenreNameIn(List<String> genreNames);

    List<Movie> findByNameContainingAndAddingDateBetweenAndGenreNameIn(String movieName, Date start, Date end,
            List<String> genreNames);

    List<Movie> findByNameContainingAndAddingDateBetween(String movieName, Date start, Date end);
}
