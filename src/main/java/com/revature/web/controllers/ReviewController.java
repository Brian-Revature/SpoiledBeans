package com.revature.web.controllers;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.services.AuthService;
import com.revature.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 *A Controller CLass which handles getting and sending of movies reviews to/form the site from/to the service layer.
 * Class handles taking in a new review form a user. Can also delete a review made by a user.
 *  Class can retrieve and send back reviews based on user name, but also sort list of reviews.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    @Autowired
    public ReviewController(final ReviewService reviewService, final AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

    //--------------------------------------General---------------------------------------------

    /**
     * Method to add a movie review to the database.
     * @param movieReviewDTO DTO which contains the movie reveiw to persist in database.
     * @param request holds the JWT to authenticate the user.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path= "/addreview",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addReview(@RequestBody final MovieReviewDTO movieReviewDTO,final HttpServletRequest request){
        movieReviewDTO.getReview().setReviewTime(new Timestamp(System.currentTimeMillis()));
        reviewService.addReview(movieReviewDTO, authService.getUserId(getToken(request)));
    }

    /**
     * Method to delete a User's movie review.
     * @param reviewsDTO DTO containing the review to be deleted.
     * @param request holds the JWT to authenticate the user.
     */
    @DeleteMapping(path= "/deletereview", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteReview(@RequestBody final ReviewsDTO reviewsDTO,final HttpServletRequest request){
        reviewService.deleteReview(reviewsDTO, authService.getUserId(getToken(request)));
    }

    //---------------------------------------Users----------------------------------------------

    /**
     * Method to get the movie reviews by the currently signed in User.
     * @param request Holds the JWT which is used to authenticate and identify the User.
     * @return DTO containing a list of all movie reviews by logged in User.
     */
    @GetMapping(path= "/myreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(final HttpServletRequest request) {
        return reviewService.getUserReviews(authService.getUserId(getToken(request)));
    }

    /**
     * MEthod to retrieve a list of movie reviews by a given User.
     * @param username Username of the user whose movie reviews are desired.
     * @return DTO containing a list of all movie reviews by the given username.
     */
    @GetMapping(path= "/userreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByUser(@RequestParam final String username) {
        return reviewService.getUserReviews(username);
    }

    /**
     * Method to get a sorted list of movie reviews by the currently signed in user. List is sorted by Rating.
     * @param ascending controls if list is sorted ascending or descending. true = ascending.
     * @param request request which hold the JWT to authenticate and identify user.
     * @return DTO containing sorted list of movie reviews by signed in user.
     */
    @GetMapping(path= "/myreviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByRating(@RequestParam final boolean ascending,final HttpServletRequest request) {
        return reviewService.getUserReviewsRatingOrder(ascending, authService.getUserId(getToken(request)));
    }

    /**
     * Method to Retrieve a sorted list of movie reviews by current user sorted by date review was made.
     * @param ascending controls if list is sorted ascending or descending. true = ascending.
     * @param request request which hold the JWT to authenticate and identify user.
     * @return DTO containing sorted list of movie reviews by signed in user.
     */
    @GetMapping(path= "/myreviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByTime(@RequestParam final boolean ascending,final HttpServletRequest request) {
        return reviewService.getUserReviewsTimeOrder(ascending, authService.getUserId(getToken(request)));
    }

    //------------------------------------------Movies--------------------------------------

    /**
     * Method to get All user reviews of a given movie.
     * @param name name of movie to get reviews of.
     * @return DTO containing list of all user reviews for the given movie.
     */
    @GetMapping(path= "/moviereviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByMovie(@RequestParam final String name) {
        return reviewService.getMovieReviews(name);
    }

    /**
     *
     * Method to get a sorted list of All user reviews of a given movie. list is sorted by rating.
     * @param name name of movie to get reviews of.
     * @param ascending controls if list is sorted ascending or descending. true = ascending.
     * @return DTO containing list of all user reviews for the given movie.
     */
    @GetMapping(path= "/moviereviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByRating(@RequestParam final boolean ascending, @RequestParam final String name) {
        return reviewService.getMovieReviewsRatingOrder(ascending, name);
    }

    /**
     * Method to get a sorted list of all User reviews of a movie. list is sorted by date of review.
     * @param name name of movie to get reviews of.
     * @param ascending controls if list is sorted ascending or descending. true = ascending.
     * @return DTO containing list of all user reviews for the given movie.
     */
    @GetMapping(path= "/moviereviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByTime(@RequestParam final boolean ascending, @RequestParam final String name) {
        return reviewService.getMovieReviewsTimeOrder(ascending, name);
    }

    //------------------------------------------Util----------------------------------------

    //helper method to get the toke from the http request.
    private String getToken(final HttpServletRequest request){
        final String token = request.getHeader("spoiledBeans-token");
        if(token.trim().equals("")){
            throw new AuthenticationException("You are not an authenticated account");
        }
        return token;
    }


}
