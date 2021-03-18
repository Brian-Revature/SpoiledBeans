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
 * Controller class which handles taking in and sending out movie reviews.
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path= "/addreview",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addReview(@RequestBody final MovieReviewDTO movieReviewDTO,final HttpServletRequest request){
        movieReviewDTO.getReview().setReviewTime(new Timestamp(System.currentTimeMillis()));
        reviewService.addReview(movieReviewDTO, authService.getUserId(getToken(request)));
    }

    @DeleteMapping(path= "/deletereview", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteReview(@RequestBody final ReviewsDTO reviewsDTO,final HttpServletRequest request){
        reviewService.deleteReview(reviewsDTO, authService.getUserId(getToken(request)));
    }

    //---------------------------------------Users----------------------------------------------

    @GetMapping(path= "/myreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(final HttpServletRequest request) {
        return reviewService.getUserReviews(authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/userreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByUser(@RequestParam final String username) {
        return reviewService.getUserReviews(username);
    }

    @GetMapping(path= "/myreviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByRating(@RequestParam boolean ascending,final HttpServletRequest request) {
        return reviewService.getUserReviewsRatingOrder(ascending, authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/myreviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByTime(@RequestParam boolean ascending,final HttpServletRequest request) {
        return reviewService.getUserReviewsTimeOrder(ascending, authService.getUserId(getToken(request)));
    }

    //------------------------------------------Movies--------------------------------------

    @GetMapping(path= "/moviereviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByMovie(@RequestParam final String name) {
        return reviewService.getMovieReviews(name);
    }

    @GetMapping(path= "/moviereviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByRating(@RequestParam final boolean ascending, @RequestParam final String name) {
        return reviewService.getMovieReviewsRatingOrder(ascending, name);
    }

    @GetMapping(path= "/moviereviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByTime(@RequestParam final boolean ascending, @RequestParam final String name) {
        return reviewService.getMovieReviewsTimeOrder(ascending, name);
    }

    //------------------------------------------Util----------------------------------------

    private String getToken(final HttpServletRequest request){
        final String token = request.getHeader("spoiledBeans-token");
        if(token.trim().equals("")){
            throw new AuthenticationException("You are not an authenticated account");
        }
        return token;
    }


}
