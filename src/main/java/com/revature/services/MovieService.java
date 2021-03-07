package com.revature.services;

import com.revature.entities.Movie;
import com.revature.exceptions.InvalidRequestException;
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

    public Movie getMovieById(int id){
        if(id <= 0){
            throw new InvalidRequestException();
        }
        return movieRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Movie getMovieByName(String name){
        return movieRepo.findMovieByName(name).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Movie> getMoviesByYear(int year){
        return movieRepo.findMoviesByYear(year);
    }
}
