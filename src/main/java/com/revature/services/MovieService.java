package com.revature.services;

import com.revature.entities.Movie;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    private MovieRepository movieRepo;

    @Autowired
    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepo.findAll();
    }
}
