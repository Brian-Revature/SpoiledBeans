package com.revature.services;

import com.revature.dtos.MovieReviewDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.ReviewRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service Class which Sends reviews to database through repo service and retrieves database form database and passes them to service controller layer
 */
@Service
public class ReviewService {

    private static final Logger LOG = LogManager.getLogger(UserService.class);
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public ReviewService(final ReviewRepository reviewRepository,final UserService userService,final MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.movieService = movieService;
    }

    //--------------------------------Core-Service-------------------------------------------------
    public void addReview(final MovieReviewDTO movieReviewDTO,final int id)    {
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
    public boolean deleteReview(final ReviewsDTO reviewsDTO,final int userId) {
        final User user = userService.getUserById(userId);
        for (Review r: user.getUserReviews()) {
            if (r.getMovie().getName().equals(reviewsDTO.getMovie())){
                user.removeReview(r);
                userService.updateUser(user);
                return true; }
        }
        return false; }
    //--------------------------------User-Related-------------------------------------------------
    private ReviewsDTO getReviewsDTO(final User user){
        final ReviewsDTO revs = new ReviewsDTO();
        revs.setUsername(user.getUsername());
        revs.setReviews(user.getUserReviews());
        final List<Movie> movies = new ArrayList<>();

        for (Review r: user.getUserReviews()) {
            movies.add(r.getMovie());
        }

        revs.setMovies(movies);
        return revs;
    }
    public ReviewsDTO getUserReviews(final int id){
        return getReviewsDTO(userService.getUserById(id));
    }
    public ReviewsDTO getUserReviews(final String username){
        return getReviewsDTO(userService.getUserByUsername(username));
    }
    public ReviewsDTO getUserReviewsRatingOrder(final boolean ascending,final int id) {
        final User user = userService.getUserById(id);
        final ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(user.getUserReviews());
        reviewsDTO.setUsername(user.getUsername());
        final Comparator<Review> compareByRating = (ascending) ?
                (Review r1, Review r2) -> Double.compare(r1.getRating(),r2.getRating()) :
                (Review r1, Review r2) -> Double.compare(r2.getRating(),r1.getRating());
        reviewsDTO.getReviews().sort(compareByRating);
        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getMovies().add(r.getMovie());
        }
        return reviewsDTO; }

    public ReviewsDTO getUserReviewsTimeOrder(final boolean ascending,final int id) {
        final ReviewsDTO reviewsDTO = new ReviewsDTO();
        final User user = userService.getUserById(id);
        reviewsDTO.setReviews(user.getUserReviews());
        reviewsDTO.setUsername(user.getUsername());

        final Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));
        reviewsDTO.getReviews().sort(compareByTime);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getMovies().add(r.getMovie());
        }

        return reviewsDTO;
    }
    //-----------------------------------------Movie-Related----------------------------------
    private ReviewsDTO getReviewsDTO(final Movie movie){
        final ReviewsDTO revs = new ReviewsDTO();
        revs.setMovie(movie.getName());
        revs.setReviews(movie.getAllReviews());
        final List<User> reviewers = new ArrayList<>();
        for (Review r: movie.getAllReviews()) {
            reviewers.add(r.getReviewer());
        }
        revs.setUsers(reviewers);
        return revs; }

    public ReviewsDTO getMovieReviews(final String movieName) {return getReviewsDTO(movieService.getMovieByName(movieName)); }

    public ReviewsDTO getMovieReviewsRatingOrder(final boolean ascending,final String movieName) {
        final Movie movie = movieService.getMovieByName(movieName);
        final ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(movie.getAllReviews());
        reviewsDTO.setMovie(movieName);

        final Comparator<Review> compareByRating = (ascending) ?
                (Review r1, Review r2) -> Double.compare(r1.getRating(),r2.getRating()) :
                (Review r1, Review r2) -> Double.compare(r2.getRating(),r1.getRating());
        reviewsDTO.getReviews().sort(compareByRating);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getUsers().add(r.getReviewer());
        }

        return reviewsDTO;
    }

    public ReviewsDTO getMovieReviewsTimeOrder(boolean ascending, final String movieName) {
        final Movie movie = movieService.getMovieByName(movieName);
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setReviews(movie.getAllReviews());
        reviewsDTO.setMovie(movieName);

        Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));
        reviewsDTO.getReviews().sort(compareByTime);

        for (Review r: reviewsDTO.getReviews()) {
            reviewsDTO.getUsers().add(r.getReviewer());
        }
        return reviewsDTO;
    }

}
