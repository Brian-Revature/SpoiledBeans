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

import java.util.ArrayList;
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
    public void addReview(MovieReviewDTO movieReviewDTO, int id)    {
        final User user = userService.getUserById(id);
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

        review.setReviewer(user);
        review.setMovie(movie);

        reviewRepository.save(review);
    }
    public boolean deleteReview(ReviewsDTO reviewsDTO, int userId) {
        final User user = userService.getUserById(userId);
        for (Review r: user.getUserReviews()) {
            if (r.getMovie().getName().equals(reviewsDTO.getMovie())){
                user.removeReview(r);
                userService.updateUser(user);
                return true; }
        }
        return false; }
    //--------------------------------User-Related-------------------------------------------------
    private ReviewsDTO getReviewsDTO(User user){
        ReviewsDTO revs = new ReviewsDTO();
        revs.setUsername(user.getUsername());
        revs.setReviews(user.getUserReviews());
        List<Movie> movies = new ArrayList<>();

        for (Review r: user.getUserReviews()) {
            movies.add(r.getMovie());
        }

        revs.setMovies(movies);
        return revs;
    }
    public ReviewsDTO getUserReviews(int id){
        return getReviewsDTO(userService.getUserById(id));
    }
    public ReviewsDTO getUserReviews(String username){
        return getReviewsDTO(userService.getUserByUsername(username));
    }
    public ReviewsDTO getUserReviewsRatingOrder(boolean ascending, int id) {
        final User user = userService.getUserById(id);
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(user.getUserReviews());
        reviewsDTO.setUsername(user.getUsername());
        Comparator<Review> compareByRating = (ascending) ?
                (Review r1, Review r2) -> Double.compare(r1.getRating(),r2.getRating()) :
                (Review r1, Review r2) -> Double.compare(r2.getRating(),r1.getRating());
        Collections.sort(reviewsDTO.getReviews(),compareByRating);
        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getMovies().add(r.getMovie());
        }
        return reviewsDTO; }
    public ReviewsDTO getUserReviewsTimeOrder(boolean ascending, int id) {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        final User user = userService.getUserById(id);
        reviewsDTO.setReviews(user.getUserReviews());
        reviewsDTO.setUsername(user.getUsername());

        Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));
        Collections.sort(reviewsDTO.getReviews(),compareByTime);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getMovies().add(r.getMovie());
        }

        return reviewsDTO;
    }
    //-----------------------------------------Movie-Related----------------------------------
    private ReviewsDTO getReviewsDTO(Movie movie){
        ReviewsDTO revs = new ReviewsDTO();
        revs.setMovie(movie.getName());
        revs.setReviews(movie.getAllReviews());
        List<User> reviewers = new ArrayList<>();
        for (Review r: movie.getAllReviews()) {
            reviewers.add(r.getReviewer());
        }
        revs.setUsers(reviewers);
        return revs; }

    public ReviewsDTO getMovieReviews(String movieName) {return getReviewsDTO(movieService.getMovieByName(movieName)); }

    public ReviewsDTO getMovieReviewsRatingOrder(boolean ascending, String movieName) {
        final Movie movie = movieService.getMovieByName(movieName);
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(movie.getAllReviews());
        reviewsDTO.setMovie(movieName);

        Comparator<Review> compareByRating = (ascending) ?
                (Review r1, Review r2) -> Double.compare(r1.getRating(),r2.getRating()) :
                (Review r1, Review r2) -> Double.compare(r2.getRating(),r1.getRating());
        Collections.sort(reviewsDTO.getReviews(),compareByRating);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getUsers().add(r.getReviewer());
        }

        return reviewsDTO;
    }

    public ReviewsDTO getMovieReviewsTimeOrder(boolean ascending, String movieName) {
        final Movie movie = movieService.getMovieByName(movieName);
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(movie.getAllReviews());
        reviewsDTO.setMovie(movieName);

        Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));
        Collections.sort(reviewsDTO.getReviews(),compareByTime);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getUsers().add(r.getReviewer());
        }
        return reviewsDTO;
    }

}
