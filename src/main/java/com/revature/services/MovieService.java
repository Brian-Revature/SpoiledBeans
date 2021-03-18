package com.revature.services;

import com.revature.dtos.MoviesDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.entities.Movie;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.MovieRepository;
import com.revature.web.intercom.OMDbClient;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *  Service Class which
 */
@Service
public class MovieService {

    private final MovieRepository movieRepo;
    private final OMDbClient omdb;

    @Autowired
    public MovieService(final MovieRepository movieRepo,final OMDbClient omdb) {
        this.movieRepo = movieRepo;
        this.omdb = omdb;
    }

    public void save(final Movie m){
        movieRepo.save(m); }

    public boolean saveNewMovie(final Movie m){
        final Optional<Movie> movie = movieRepo.findMovieByName(m.getName());
        if(movie.isPresent()){
            return false; }
        movieRepo.save(omdb.getMovieInformation(m.getName()));
        return true;
    }

    public boolean saveNewMovie(final MoviesDTO m){
        final Optional<Movie> movie = movieRepo.findMovieByName(m.getName());
        if(movie.isPresent()){
            return false; }
        movieRepo.save(omdb.getMovieInformation(m.getName()));
        return true;
    }

    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepo.findAll();
    }

    public Movie getMovieById(final int id){
        if(id <= 0){
            throw new InvalidRequestException(); }
        final Movie m = movieRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
        try {
            m.setRating(movieRepo.findMovieRating(m.getName()));
        }catch(AopInvocationException e){
            m.setRating(0);
        }
        return m;
    }

    public Movie getMovieByName(final String name){
        final Movie m = movieRepo.findMovieByName(name).orElseThrow(ResourceNotFoundException::new);
        try {
            m.setRating(movieRepo.findMovieRating(name));
        }catch(AopInvocationException e){
            m.setRating(0);
        }
        return m;
    }

    public List<Movie> getMoviesByYear(final int year){
        return movieRepo.findMoviesByYear(year);
    }

    public List<Movie> getMoviesByGenre(final String genre){
        return movieRepo.findMoviesByGenre(genre);
    }

    public List<Movie> getMoviesByDirector(final String director){
        return movieRepo.findMoviesByDirector(director);
    }

    public ReviewsDTO getReviewDTO(final Movie movie){
        final ReviewsDTO revs = new ReviewsDTO();
        revs.setMovie(movie.getName());
        revs.setReviews(movie.getAllReviews());
        return revs;
    }
}
