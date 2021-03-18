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
 *  Service Class which Handles retrieving and sending movie data.
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

    /**
     * Method to save a movie to the database.
     * @param m movie to save to database.
     */
    public void save(final Movie m){
        movieRepo.save(m); }

    /**
     * Method to save a new movie to the database using a movie object..
     * @param m movie to save to database.
     * @return boolean to indicate success/failure.
     */
    public boolean saveNewMovie(final Movie m){
        final Optional<Movie> movie = movieRepo.findMovieByName(m.getName());
        if(movie.isPresent()){
            return false; }
        movieRepo.save(omdb.getMovieInformation(m.getName()));
        return true;
    }

    /**
     * Method to dave a new movie to the database using a movieDTO.
     * @param m movie to save to database.
     * @return boolean to indicate success/failure.
     */
    public boolean saveNewMovie(final MoviesDTO m){
        final Optional<Movie> movie = movieRepo.findMovieByName(m.getName());
        if(movie.isPresent()){
            return false; }
        movieRepo.save(omdb.getMovieInformation(m.getName()));
        return true;
    }

    /**
     * MEthod to return a list of all movies currently in database.
     * @return List of all currently save movies in database.
     */
    public List<Movie> getAllMovies(){
        return (List<Movie>) movieRepo.findAll();
    }

    /**
     * MEthdo to get a single movie by its id.
     * @param id id of movie to retrieve.
     * @return Movie which has the matching id.
     */
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

    /**
     * Methdo to get a movie by its name.
     * @param name name of movie to retrieve.
     * @return movie with the matching name.
     */
    public Movie getMovieByName(final String name){
        final Movie m = movieRepo.findMovieByName(name).orElseThrow(ResourceNotFoundException::new);
        try {
            m.setRating(movieRepo.findMovieRating(name));
        }catch(AopInvocationException e){
            m.setRating(0);
        }
        return m;
    }

    /**
     * Method to get a sorted list of movies with the given release year.
     * @param year year of movie's release.
     * @return list of all movies in database which were rleased on the given year.
     */
    public List<Movie> getMoviesByYear(final int year){
        return movieRepo.findMoviesByYear(year);
    }

    /**
     * Method to get a list of all movies matching a given genre.
     * @param genre genre of movie to sort by.
     * @return list containing all movies in database with given genre.
     */
    public List<Movie> getMoviesByGenre(final String genre){
        return movieRepo.findMoviesByGenre(genre);
    }

    /**
     * Method to get a list of movies with the same director.
     * @param director director of movies.
     * @return List of movies in database which all have the same director.
     */
    public List<Movie> getMoviesByDirector(final String director){
        return movieRepo.findMoviesByDirector(director);
    }

    /**
     * MEthod to convert a movie object into a ReviewDTO
     * @param movie movie to convert into a ReviewDTO.
     * @return reviewDTO of given movie.
     */
    public ReviewsDTO getReviewDTO(final Movie movie){
        final ReviewsDTO revs = new ReviewsDTO();
        revs.setMovie(movie.getName());
        revs.setReviews(movie.getAllReviews());
        return revs;
    }
}
