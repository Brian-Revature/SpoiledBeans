package com.revature.services;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.repos.ReviewRepository;
import com.revature.repos.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewServiceTester {

    @Mock
    ReviewRepository mockReviewRepo;
    @Mock
    MovieService mockMovieService;
    @Mock
    UserService mockUserService;
    @InjectMocks
    ReviewService mockReviewService;

    public User fullUser;
    public Movie up;
    public Movie alien;
    public MovieReviewDTO movieReviewDTO;
    public MovieReviewDTO badMovieReviewDTO;

    public Review goodReview;
    public Review badReview;
    public Review horrorReview;

    public ReviewsDTO reviewsDTO;
    List<Review> orderedReviewsDesc;
    List<Review> orderedReviewsAsc;

    @Before
    public void setUp(){
        fullUser = new User("User", "Pass", "ValidGmail@Gmail.com","Brian",
                "Withrow","I want to make movie reviews!" );

        up = new Movie();
        up.setId(1);
        up.setName("Up");
        up.setDirector("UpDirector");
        up.setGenre("Family Adventure");
        up.setSynopsis("Old man and kid go on a high stakes adventure.");
        up.setYear(2010);

        alien = new Movie();
        alien.setId(2);
        alien.setName("Alien");
        alien.setDirector("AlienDirector");
        alien.setGenre("Horror Adventure");
        alien.setSynopsis("Lady kills alien.");
        alien.setYear(1980);

        goodReview = new Review();
        goodReview.setReview("Really loved it! Fun all around.");
        goodReview.setRating(5.0);

        badReview = new Review();
        badReview.setReview("I really hated it. Terrible.");
        badReview.setRating(1.0);
        badReview.setReviewer(fullUser);

        horrorReview = new Review();
        horrorReview.setReview("Was sooo scary, but not too much.");
        horrorReview.setRating(4.0);
        horrorReview.setReviewer(fullUser);

        movieReviewDTO = new MovieReviewDTO();
        movieReviewDTO.setMovie(up);
        movieReviewDTO.setReview(goodReview);

        badMovieReviewDTO = new MovieReviewDTO();
        badMovieReviewDTO.setMovie(up);
        badMovieReviewDTO.setReview(badReview);

        orderedReviewsAsc = new ArrayList<>();
        orderedReviewsAsc.add(badReview);
        orderedReviewsAsc.add(horrorReview);
        orderedReviewsAsc.add(goodReview);

        orderedReviewsDesc = new ArrayList<>();
        orderedReviewsDesc.add(goodReview);
        orderedReviewsDesc.add(horrorReview);
        orderedReviewsDesc.add(badReview);

        reviewsDTO = new ReviewsDTO();
        reviewsDTO.setMovie("Up");

        MockitoAnnotations.initMocks(this);
    }

    //------------------------------------------------AddReviews--------------------------------------------------------

    @Test
    public void addReviewNonePreviously(){
        //Arrange
        when(mockUserService.getUserById(1)).thenReturn(fullUser);
        when(mockMovieService.saveNewMovie(up)).thenReturn(true);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);
        when(mockReviewRepo.save(goodReview)).thenReturn(goodReview);

        //Act
        mockReviewService.addReview(movieReviewDTO, 1);

        //Assert
        Assert.assertEquals(goodReview.getReviewer(), fullUser);
        Assert.assertEquals(up, goodReview.getMovie());
    }

    @Test
    public void addReviewChangeReview(){
        //Arrange
        when(mockUserService.getUserById(1)).thenReturn(fullUser);
        when(mockMovieService.saveNewMovie(up)).thenReturn(false);
        when(mockReviewRepo.save(badReview)).thenReturn(badReview);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);
        goodReview.setId(1);
        fullUser.addReview(goodReview);
        up.addReview(goodReview);
        when(mockReviewRepo.findReviewById(1)).thenReturn(Optional.of(goodReview));

        //Act
        mockReviewService.addReview(badMovieReviewDTO, 1);

        //Assert
        Assert.assertEquals(goodReview.getReview(),badReview.getReview());
    }

    //---------------------------------------------DeleteReviews--------------------------------------------------------

    @Test
    public void deleteReviewFound(){
        //Arrange
        fullUser.addReview(goodReview);
        goodReview.setMovie(up);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);

        //Act
        boolean deleted = mockReviewService.deleteReview(reviewsDTO, 1);

        //Assert
        Assert.assertEquals(true, deleted);
    }

    @Test
    public void deleteReviewNotFound(){
        //Arrange
        fullUser.addReview(goodReview);
        goodReview.setMovie(alien);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);

        //Act
        boolean deleted = mockReviewService.deleteReview(reviewsDTO, 1);

        //Assert
        Assert.assertEquals(false, deleted);
    }

    //---------------------------------------------getUserReviews-------------------------------------------------------

    @Test
    public void getUserReviewsID(){
        //Arrange
        when(mockUserService.getUserById(1)).thenReturn(fullUser);
        fullUser.addReview(goodReview);
        goodReview.setMovie(up);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviews(1);

        //Assert
        Assert.assertEquals(goodReview,reviewsDTOCheck.getReviews().get(0));
    }

    @Test
    public void getUserReviewsUsername(){
        //Arrange
        when(mockUserService.getUserByUsername("User")).thenReturn(fullUser);
        fullUser.addReview(goodReview);
        goodReview.setMovie(up);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviews(fullUser.getUsername());

        //Assert
        Assert.assertEquals(goodReview,reviewsDTOCheck.getReviews().get(0));
    }

    @Test
    public void getUserReviewsDescRating(){
        //Arrange
        fullUser.addReview(badReview);
        fullUser.addReview(goodReview);
        fullUser.addReview(horrorReview);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviewsRatingOrder(false,1);

        //Assert
        Assert.assertEquals(orderedReviewsDesc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getUserReviewsAscRating(){
        //Arrange
        fullUser.addReview(badReview);
        fullUser.addReview(goodReview);
        fullUser.addReview(horrorReview);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviewsRatingOrder(true,1);

        //Assert
        Assert.assertEquals(orderedReviewsAsc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getUserReviewsDescTime(){
        //Arrange
        fullUser.addReview(badReview);
        fullUser.addReview(goodReview);
        fullUser.addReview(horrorReview);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);
        badReview.setReviewTime(new Timestamp(new Date().getTime() + 100));
        horrorReview.setReviewTime(new Timestamp(new Date().getTime() + 50));
        goodReview.setReviewTime(new Timestamp(new Date().getTime()));


        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviewsTimeOrder(true,1);

        //Assert
        Assert.assertEquals(orderedReviewsDesc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getUserReviewsAscTime(){
        //Arrange
        fullUser.addReview(badReview);
        fullUser.addReview(goodReview);
        fullUser.addReview(horrorReview);
        when(mockUserService.getUserById(1)).thenReturn(fullUser);
        badReview.setReviewTime(new Timestamp(new Date().getTime() + 100));
        horrorReview.setReviewTime(new Timestamp(new Date().getTime() + 50));
        goodReview.setReviewTime(new Timestamp(new Date().getTime()));


        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getUserReviewsTimeOrder(false,1);

        //Assert
        Assert.assertEquals(orderedReviewsAsc,reviewsDTOCheck.getReviews());
    }

    //--------------------------------------------getMovieReviews-------------------------------------------------------

    @Test
    public void getMovieReviews(){
        //Arrange
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);
        fullUser.addReview(goodReview);
        up.addReview(goodReview);
        up.addReview(badReview);
        goodReview.setMovie(up);
        goodReview.setReviewer(fullUser);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getMovieReviews(up.getName());

        //Assert
        Assert.assertEquals(goodReview,reviewsDTOCheck.getReviews().get(0));
    }

    @Test
    public void getMovieReviewDescRating(){
        //Arrange
        up.addReview(badReview);
        up.addReview(goodReview);
        up.addReview(horrorReview);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getMovieReviewsRatingOrder(false,"Up");

        //Assert
        Assert.assertEquals(orderedReviewsDesc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getMovieReviewAscRating(){
        //Arrange
        up.addReview(badReview);
        up.addReview(goodReview);
        up.addReview(horrorReview);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);

        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getMovieReviewsRatingOrder(true,"Up");

        //Assert
        Assert.assertEquals(orderedReviewsAsc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getMovieReviewsDescTime(){
        //Arrange
        up.addReview(badReview);
        up.addReview(goodReview);
        up.addReview(horrorReview);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);
        badReview.setReviewTime(new Timestamp(new Date().getTime()));
        horrorReview.setReviewTime(new Timestamp(new Date().getTime() + 50));
        goodReview.setReviewTime(new Timestamp(new Date().getTime() + 100));


        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getMovieReviewsTimeOrder(false,"Up");

        //Assert
        Assert.assertEquals(orderedReviewsDesc,reviewsDTOCheck.getReviews());
    }

    @Test
    public void getMovieReviewsAscTime(){
        //Arrange
        up.addReview(badReview);
        up.addReview(goodReview);
        up.addReview(horrorReview);
        when(mockMovieService.getMovieByName("Up")).thenReturn(up);
        badReview.setReviewTime(new Timestamp(new Date().getTime() + 100));
        horrorReview.setReviewTime(new Timestamp(new Date().getTime() + 50));
        goodReview.setReviewTime(new Timestamp(new Date().getTime()));


        //Act
        ReviewsDTO reviewsDTOCheck = mockReviewService.getMovieReviewsTimeOrder(true,"Up");

        //Assert
        Assert.assertEquals(orderedReviewsDesc,reviewsDTOCheck.getReviews());
    }
}
