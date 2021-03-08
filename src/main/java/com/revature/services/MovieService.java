package com.revature.services;

import com.revature.entities.Movie;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.MovieRepository;
import com.revature.web.intercom.OMDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    private MovieRepository movieRepo;
    private OMDbClient omdb;

    @Autowired
    public MovieService(MovieRepository movieRepo, OMDbClient omdb) {
        this.movieRepo = movieRepo;
        this.omdb = omdb;
    }

    public void save(Movie m){
        movieRepo.save(m);
    }

    public boolean saveNewMovie(Movie m){
        Optional<Movie> movie = movieRepo.findMovieByName(m.getName());
        if(movie.isPresent()){
            return false;
        }
        movieRepo.save(omdb.getMovieInformation(m.getName()));
        return true;
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

    public List<Movie> getMoviesByGenre(String genre){
        return movieRepo.findMoviesByGenre(genre);
    }

    public List<Movie> getMoviesByDirector(String director){
        return movieRepo.findMoviesByDirector(director);
    }
}
