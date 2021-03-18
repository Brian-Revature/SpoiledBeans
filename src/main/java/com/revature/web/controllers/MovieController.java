package com.revature.web.controllers;

import com.revature.dtos.MoviesDTO;
import com.revature.entities.Movie;
import com.revature.services.MovieService;
import com.revature.web.annotations.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;


/**
 *  This is the collection of endpoints for movies
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    /**
     *  gets the reference to the movieService by using the one passed in
     * @param movieService  the inbetween for the controller and the MovieRepository that handles the business logic
     */
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    /**
     * endpoint to return a list of movies
     * @return  returns a list of movies
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    /**
     * endpoint to return a movie given an id associated with it
     * @param id Takes in an id to find a specific movie
     * @return return a specific movie
     */
    @GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovieById(@RequestParam int id){
        return movieService.getMovieById(id);
    }

    /**
     * endpoint to return a movie given a name associated with it
     * @param name Takes in an name to find a specific movie
     * @return return a specific movie
     */
    @GetMapping(path = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovieByName(@RequestParam String name){
        return movieService.getMovieByName(name);
    }

    /**
     * endpoint to return a list of movies given an year associated with it
     * @param year Takes in an year to find a list of movies
     * @return return a list of movies
     */
    @GetMapping(path = "/year", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByYear(@RequestParam int year){
        return movieService.getMoviesByYear(year);
    }

    /**
     * endpoint to return a list of movies given an genre associated with it
     * @param genre Takes in an genre to find a list of movies
     * @return return a list of movies
     */
    @GetMapping(path = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByGenre(@RequestParam String genre){
        return movieService.getMoviesByGenre(genre);
    }
    /**
     * endpoint to return a list of movies given an director associated with it
     * @param director Takes in an director to find a list of movies
     * @return return a list of movies
     */
    @GetMapping(path = "/director", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByDirector(@RequestParam String director){
        return movieService.getMoviesByDirector(director);
    }

    //---------------------------------------------------------------------------

    /**
     *  endpoint where the director for a movie passed in is updated by the admin
     * @param m a movie object passed in
     */
    @Secured(allowedRoles = {"Admin"})
    @PutMapping(path = "/director", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setDirector(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setDirector(m.getDirector());
        movieService.save(movie);
    }

    /**
     *  endpoint where the genre for a movie passed in is updated by the admin
     * @param m a movie object passed in
     */
    @Secured(allowedRoles = {"Admin"})
    @PutMapping(path = "/genre", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setGenre(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setGenre(m.getGenre());
        movieService.save(movie);
    }

    /**
     *  endpoint where the synopsis for a movie passed in is updated by the admin
     * @param m a movie object passed in
     */
    @Secured(allowedRoles = {"Admin"})
    @PutMapping(path = "/synopsis", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setSynopsis(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setSynopsis(m.getSynopsis());
        movieService.save(movie);
    }

    //---------------------------------------------------------------------------

    /**
     * When querying the OMDb database, if a year is not specified, the most recent movie will be returned
     *  endpoint where the admin adds a movie
     * @param m a movie object passed in
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addMovieByName(@RequestBody Movie m){
        movieService.saveNewMovie(m);
    }
}
