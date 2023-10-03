package com.example.demo.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
