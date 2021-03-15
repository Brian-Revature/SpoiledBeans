package com.revature.web.controllers;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.MoviesDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.dtos.UserDTO;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.exceptions.AuthenticationException;
import com.revature.services.AuthService;
import com.revature.services.ReviewService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    @Autowired
    public ReviewController(ReviewService reviewService, AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

    //--------------------------------------General---------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path= "/addreview",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addReview(@RequestBody final MovieReviewDTO movieReviewDTO, HttpServletRequest request, @RequestBody User user){
        movieReviewDTO.getReview().setReviewTime(new Timestamp(System.currentTimeMillis()));
        reviewService.addReview(movieReviewDTO, user.getId());
        //reviewService.addReview(movieReviewDTO, authService.getUserId(getToken(request)));
    }

    @DeleteMapping(path= "/deletereview", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteReview(@RequestBody final ReviewsDTO reviewsDTO, HttpServletRequest request, @RequestBody User user){
        reviewService.deleteReview(reviewsDTO, user.getId());
        //reviewService.deleteReview(reviewsDTO, authService.getUserId(getToken(request)));
    }

    //---------------------------------------Users----------------------------------------------

    @GetMapping(path= "/myreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(HttpServletRequest request, @RequestBody User user) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviews(user.getId());
        //return reviewService.getUserReviews(authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/userreviews",produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestBody UserDTO userdto) {
        return reviewService.getUserReviews(userdto.getUsername());
    }

    @GetMapping(path= "/myreviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByRating(@RequestParam boolean ascending, HttpServletRequest request, @RequestBody User user) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviewsRatingOrder(ascending, user.getId());
        //return reviewService.getUserReviewsRatingOrder(ascending, authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/myreviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviewsByTime(@RequestParam boolean ascending, HttpServletRequest request, @RequestBody User user) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviewsTimeOrder(ascending, user.getId());
        //return reviewService.getUserReviewsTimeOrder(ascending, authService.getUserId(getToken(request)));
    }

    //------------------------------------------Movies--------------------------------------

    @GetMapping(path= "/moviereviews",produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestBody MoviesDTO moviesDTO) {
        return reviewService.getMovieReviews(moviesDTO.getName());
    }

    @GetMapping(path= "/moviereviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByRating(@RequestParam boolean ascending, @RequestBody MoviesDTO moviesDTO) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getMovieReviewsRatingOrder(ascending, moviesDTO.getName());
    }

    @GetMapping(path= "/moviereviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getMovieReviewsByTime(@RequestParam boolean ascending, @RequestBody MoviesDTO moviesDTO) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getMovieReviewsTimeOrder(ascending, moviesDTO.getName());
    }

    //------------------------------------------Util----------------------------------------

    private String getToken(HttpServletRequest request){
        Cookie[] reqCookies = request.getCookies();

        if (reqCookies == null) {
            throw new AuthenticationException("An unauthenticated request was made to a protected endpoint!");
        }

        return Stream.of(reqCookies)
                .filter(c -> c.getName().equals("spoiledBeans-token"))
                .findFirst()
                .orElseThrow(AuthenticationException::new)
                .getValue();
    }


}
