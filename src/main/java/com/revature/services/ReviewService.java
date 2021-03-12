package com.revature.services;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.ReviewRepository;
import com.revature.repos.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger LOG = LogManager.getLogger(UserService.class);
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserService userService, MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.movieService = movieService;
    }

    //--------------------------------Core-Service-------------------------------------------------
    //TODO replace user search with active user
    public void addReview(MovieReviewDTO movieReviewDTO) {
        final User user = userService.getUserById(1);
        boolean movieDoesntExist = movieService.saveNewMovie(movieReviewDTO.getMovie());
        final Movie movie = movieService.getMovieByName(movieReviewDTO.getMovie().getName());
        Review review = movieReviewDTO.getReview();
        boolean found = false;

        if (!movieDoesntExist){
            List<Review> reviewList = user.getUserReviews();
            List<Review> moviesRevList = movie.getAllReviews();
            for (Review r: reviewList) {
                for (Review mr: moviesRevList) {
                    if (mr.equals(r)){
                        review = reviewRepository.findReviewById(mr.getId()).orElseThrow(ResourceNotFoundException::new);
                        review.setReview(movieReviewDTO.getReview().getReview());
                        review.setRating(movieReviewDTO.getReview().getRating());
                        review.setReviewTime(movieReviewDTO.getReview().getReviewTime());
                        found = true;
                        reviewRepository.save(review);
                        return;
                    }
                }
            }
        }

        review.addMovieReview(movie);
        review.addReviewer(user);

        reviewRepository.save(review);
    }

    //--------------------------------User-Related-------------------------------------------------

    private ReviewsDTO getReviewsDTO(User user){
        ReviewsDTO revs = new ReviewsDTO();
        revs.setUsername(user.getUsername());
        revs.setReviews(user.getUserReviews());
        return revs;
    }

    //TODO replace user with active user
    public ReviewsDTO getUserReviews(int id){
        return getReviewsDTO(userService.getUserById(id));
    }

    public ReviewsDTO getUserReviews(String username){
        return getReviewsDTO(userService.getUserByUsername(username));
    }

    //TODO replace user search with active user
    //Function used to sort by Ascending/Descending review ratings
    public List<Review> getUserReviewsRatingOrder(boolean ascending) {
        final User user = userService.getUserById(1);
        List<Review> reviews = user.getUserReviews();
        Comparator<Review> compareByRating = (ascending) ?
                (Review r1, Review r2) -> Double.compare(r1.getRating(),r2.getRating()) :
                (Review r1, Review r2) -> Double.compare(r2.getRating(),r1.getRating());
        Collections.sort(reviews,compareByRating);
        return reviews;
    }

    //TODO replace user search with active user
    //Function used to sort by Ascending/Descending timestamp ratings
    public List<Review> getUserReviewsTimeOrder(boolean ascending) {
        final User user = userService.getUserById(1);
        List<Review> reviews = user.getUserReviews();
        Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));
        Collections.sort(reviews,compareByTime);
        return reviews;
    }

    //-----------------------------------------Movie-Related----------------------------------

//    public List<Review> getMovieReviews(int id){
//
//    }
}
