package com.revature.services;

import com.revature.dtos.*;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.UserRepository;
import com.revature.web.intercom.OMDbClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private final UserRepository userRepo;
    private final MovieService movieService;
    private final OMDbClient omdb;

    @Autowired
    public UserService(UserRepository repo, MovieService movieService, OMDbClient omdb) {
        super();
        this.userRepo = repo;
        this.movieService = movieService;
        this.omdb = omdb;
    }

    private void mapUserFromDTO(final User user, final UserDTO userdto) {
        if(userdto.getUsername() != null && !userdto.getUsername().trim().equals("")) {
            user.setUsername(userdto.getUsername());
        }
        if(userdto.getPassword() != null && !userdto.getPassword().trim().equals("")) {
            user.setPassword(userdto.getPassword());
        }
        if(userdto.getFirstName() != null && !userdto.getFirstName().trim().equals("")) {
            user.setFirstName(userdto.getFirstName());
        }
        if(userdto.getLastName() != null && !userdto.getLastName().trim().equals("")) {
            user.setLastName(userdto.getLastName());
        }
        if(userdto.getEmail() != null && !userdto.getEmail().trim().equals("")) {
            user.setEmail(userdto.getEmail());
        }
        if(userdto.getBio() != null && !userdto.getBio().trim().equals("")) {
            user.setBio(userdto.getBio());
        }
    }

    public void registerNewUser(final UserDTO userdto) {
        final User user = new User();
        mapUserFromDTO(user,userdto);
        userRepo.save(user);

    }

    public User getUserById(int id) {
        if (id <= 0 ) {
            throw new InvalidRequestException();
        }
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public User getUserByUsername(String username){

        if(username==null || username.trim().equals("")){
            throw new InvalidRequestException("Username cannot be empty or null");
        }
        return userRepo.findUserByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    //TODO: verify everything is in order when JWT established
    public void save(User u) {
        userRepo.save(u);
    }

    public void updateUser(final UserDTO userdto, final int user_id) {
        final User user = getUserById(user_id);
        mapUserFromDTO(user,userdto);
        userRepo.save(user);
    }

    private FavoritesDTO getFavoritesDTO(User user){

        FavoritesDTO favs = new FavoritesDTO();
        favs.setUsername(user.getUsername());
        favs.setFavorites(user.getUserFavorites());
        return favs;

    }

    public FavoritesDTO getUserFavorites(int id){

        return getFavoritesDTO(getUserById(id));

    }

    public FavoritesDTO getUserFavorites(String username){

        return getFavoritesDTO(getUserByUsername(username));
    }

    private ReviewsDTO getReviewsDTO(User user){

        ReviewsDTO revs = new ReviewsDTO();
        revs.setUsername(user.getUsername());
        revs.setReviews(user.getUserReviews());
        return revs;

    }

    public ReviewsDTO getUserReviews(int id){

        return getReviewsDTO(getUserById(id));

    }

    public ReviewsDTO getUserReviews(String username){

        return getReviewsDTO(getUserByUsername(username));
    }

    //TODO replace user search with current user.
    public void addFavorite(final MoviesDTO moviesDTO) {
        movieService.saveNewMovie(moviesDTO);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        final User user = getUserById(1);
        user.addMovieToFavorites(movie);
        System.out.println("user id " + user.getId());
        System.out.println("movie id " + movie.getId());
        userRepo.save(user);

    }

    //TODO replace user search with current user.
    public void deleteUserFavorite(final MoviesDTO moviesDTO) {
        final User user = getUserById(1);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        user.removeMovieFromFavorites(movie);
        userRepo.save(user);
    }

    //TODO replace user search with active user
    //Function used to sort by Ascending/Descending review ratings
    public List<Review> getUserReviewsRatingOrder(boolean ascending) {
        final User user = getUserById(1);
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
        final User user = getUserById(1);
        List<Review> reviews = user.getUserReviews();

        Comparator<Review> compareByTime = (ascending) ?
                (Review r1, Review r2) -> (r1.getReviewTime().compareTo(r2.getReviewTime())) :
                (Review r1, Review r2) -> (r2.getReviewTime().compareTo(r1.getReviewTime()));

        Collections.sort(reviews,compareByTime);

        return reviews;
    }

    //TODO replace user search with active user
    public void addReview(MovieReviewDTO movieReviewDTO) {
        movieService.saveNewMovie(movieReviewDTO.getMovie());
        final User user = getUserById(1);
        user.addReview(movieReviewDTO.getReview());
        userRepo.save(user);
    }

    //TODO replace user search with active user
    public List<Movie> getUserFavoritesByName(boolean ascending) {
        final User user = getUserById(1);
        List<Movie> movies = user.getUserFavorites();

        Comparator<Movie> compareByName = (ascending) ?
                (Movie m1, Movie m2) -> m1.getName().compareTo(m2.getName()) :
                (Movie m1, Movie m2) -> m2.getName().compareTo(m1.getName());


        Collections.sort(movies,compareByName);

        return movies;
    }
}
