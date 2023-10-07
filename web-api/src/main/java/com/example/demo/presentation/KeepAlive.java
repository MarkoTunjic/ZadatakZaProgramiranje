package com.example.demo.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("keepAlive")
public class KeepAlive {

    @GetMapping("")
    public ResponseEntity<String> getAllMovies() {
        return ResponseEntity.ok("alive");
    }
}
