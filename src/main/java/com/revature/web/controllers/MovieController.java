package com.revature.web.controllers;

import com.revature.entities.Movie;
import com.revature.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }
}
