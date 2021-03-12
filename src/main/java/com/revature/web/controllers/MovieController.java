package com.revature.web.controllers;

import com.revature.entities.Movie;
import com.revature.services.MovieService;
import com.revature.web.annotations.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovieById(@RequestParam int id){
        return movieService.getMovieById(id);
    }

    @GetMapping(path = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovieByName(@RequestParam String name){
        return movieService.getMovieByName(name);
    }

    @GetMapping(path = "/year", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByYear(@RequestParam int year){
        return movieService.getMoviesByYear(year);
    }

    @GetMapping(path = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByGenre(@RequestParam String genre){
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping(path = "/director", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getMoviesByDirector(@RequestParam String director){
        return movieService.getMoviesByDirector(director);
    }

    //---------------------------------------------------------------------------

    //TODO verify the user updating the movie is an Admin
    @Secured(allowedRoles = {"Admin"})
    @PutMapping(path = "/director", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setDirector(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setDirector(m.getDirector());
        movieService.save(movie);
    }

    //TODO verify the user updating the movie is an Admin
    @PutMapping(path = "/genre", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setGenre(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setGenre(m.getGenre());
        movieService.save(movie);
    }

    //TODO verify the user updating the movie is an Admin
    @PutMapping(path = "/synopsis", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public void setSynopsis(@RequestBody Movie m){
        Movie movie = movieService.getMovieByName(m.getName());
        movie.setSynopsis(m.getSynopsis());
        movieService.save(movie);
    }

    //---------------------------------------------------------------------------

    //When querying the OMDb database, if a year is not specified, the most recent movie will be returned

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addMovieByName(@RequestBody Movie m){
        if(movieService.saveNewMovie(m)){
            System.out.println("movie saved");
        }else{
            System.out.println("movie already in DB");
        }
    }
}
