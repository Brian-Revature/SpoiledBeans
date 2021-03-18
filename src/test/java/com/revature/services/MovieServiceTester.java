package com.revature.services;

import com.revature.dtos.MoviesDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.entities.Movie;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.MovieRepository;
import com.revature.web.intercom.OMDbClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class MovieServiceTester {

    @Mock
    MovieRepository mockMovieRepo;
    @Mock
    OMDbClient mockOMDb;
    @InjectMocks
    MovieService mockMovieService;

    public Movie fullMovie;
    public Movie emptyMovie;
    public ReviewsDTO reviewsDTO;
    public MoviesDTO moviesDTO;

    public List<Movie> movieList;
    public Movie hotRod;
    public Movie thatsMyBoy;
    public Movie popstar;

    @Before
    public void setUp(){

        fullMovie = new Movie("White Lightning","Joseph Sargent",1973);
        emptyMovie = new Movie();

        moviesDTO = new MoviesDTO();

        hotRod = new Movie();
        hotRod.setId(1);
        hotRod.setName("Hot Rod");
        hotRod.setDirector("Akiva Schaffer");
        hotRod.setGenre("Comedy");
        hotRod.setSynopsis("Rod Kimble!");
        hotRod.setYear(2007);

        thatsMyBoy = new Movie();
        thatsMyBoy.setId(2);
        thatsMyBoy.setName("That's My Boy");
        thatsMyBoy.setDirector("Sean Anders");
        thatsMyBoy.setGenre("Comedy");
        thatsMyBoy.setSynopsis("It's insulin you dick!");
        thatsMyBoy.setYear(2012);

        popstar = new Movie();
        popstar.setId(3);
        popstar.setName("Popstar: Never Stop Never Stopping");
        popstar.setDirector("Jorma Taccone");
        popstar.setGenre("Comedy");
        popstar.setSynopsis("Style Boyz are back!");
        popstar.setYear(2016);

        movieList = new ArrayList<>();
        movieList.add(hotRod);
        movieList.add(thatsMyBoy);
        movieList.add(popstar);

        reviewsDTO = new ReviewsDTO();

        MockitoAnnotations.initMocks(this);
    }

    public void tearDown(){
        movieList.clear();
        moviesDTO = new MoviesDTO();
    }

    // Register new movie ------------------------------------------

    @Test
    public void test_validSave(){
        mockMovieService.saveNewMovie(fullMovie);
    }

    @Test
    public void testInvalidMovieSave(){
        mockMovieService.saveNewMovie(emptyMovie);
    }

    @Test
    public void testValidMovieDTOSave(){
        moviesDTO.setName("A Quiet Place");
        moviesDTO.setDirector("John Krasinski");
        moviesDTO.setYear(LocalDate.of(2018, 7, 15));
        mockMovieService.saveNewMovie(moviesDTO);
    }

    @Test
    public void testInvalidMovieDTOSave(){
        mockMovieService.saveNewMovie(moviesDTO);
    }

    //Get movie by id ----------------------------------------------------

    @Test(expected = InvalidRequestException.class)
    public void testInvalidMovieId(){
        mockMovieService.getMovieById(-1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInvalidMovieWithRightId(){
        when(mockMovieRepo.findById(4)).thenThrow(ResourceNotFoundException.class);
        mockMovieService.getMovieById(4);
    }

    @Test
    public void testValidMovieId(){
        when(mockMovieRepo.findById(1)).thenReturn(Optional.of(hotRod));

        Movie m = mockMovieService.getMovieById(1);

        Assert.assertEquals(hotRod, m);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInvalidMovieName(){
        mockMovieService.getMovieByName("");
    }

    @Test
    public void testValidMovieName(){
        when(mockMovieRepo.findMovieByName("Hot Rod")).thenReturn(Optional.of(hotRod));

        Movie m = mockMovieService.getMovieByName("Hot Rod");

        Assert.assertEquals(hotRod, m);
    }

    //Get movies by year ------------------------------------------------

    @Test
    public void testValidYear(){
        when(mockMovieRepo.findMoviesByYear(2000)).thenReturn(movieList);
        ArrayList<Movie> arr = (ArrayList<Movie>) mockMovieService.getMoviesByYear(2000);
        Assert.assertEquals(movieList, arr);
    }

    @Test
    public void testValidGenre(){
        when(mockMovieRepo.findMoviesByGenre("Comedy")).thenReturn(movieList);
        ArrayList<Movie> arr = (ArrayList<Movie>) mockMovieService.getMoviesByGenre("Comedy");
        Assert.assertEquals(movieList, arr);
    }

    @Test
    public void testValidDirector(){
        when(mockMovieRepo.findMoviesByDirector("Akiva")).thenReturn(movieList);
        ArrayList<Movie> arr = (ArrayList<Movie>) mockMovieService.getMoviesByDirector("Akiva");
        Assert.assertEquals(movieList, arr);
    }

    @Test
    public void testGetReviewDTO(){
        reviewsDTO.setMovie(hotRod.getName());
        reviewsDTO.setReviews(hotRod.getAllReviews());

        ReviewsDTO rdto = mockMovieService.getReviewDTO(hotRod);

        Assert.assertEquals(reviewsDTO, rdto);
    }

    @Test
    public void testGetAllMovies(){
        when(mockMovieRepo.findAll()).thenReturn(movieList);

        ArrayList<Movie> all = (ArrayList<Movie>) mockMovieService.getAllMovies();

        Assert.assertEquals(movieList, all);

    }
}
