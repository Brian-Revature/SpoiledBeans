package com.revature.web.controllers;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.dtos.UserDTO;
import com.revature.entities.Review;
import com.revature.services.ReviewService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(path= "/myreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestParam int id) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviews(id);
    }

    @GetMapping(path= "/userreviews",produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestBody UserDTO userdto) {
        return reviewService.getUserReviews(userdto.getUsername());
    }

    @GetMapping(path= "/myreviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getUserReviewsByRating(@RequestParam boolean ascending) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviewsRatingOrder(ascending);
    }

    @GetMapping(path= "/myreviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getUserReviewsByTime(@RequestParam boolean ascending) {
        //Not clear how we are getting the user id or user data at this point
        return reviewService.getUserReviewsTimeOrder(ascending);
    }

    @PostMapping(path= "/addreview",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addReview(@RequestBody final MovieReviewDTO movieReviewDTO){
        movieReviewDTO.getReview().setReviewTime(new Timestamp(System.currentTimeMillis()));
        reviewService.addReview(movieReviewDTO);
    }


}
