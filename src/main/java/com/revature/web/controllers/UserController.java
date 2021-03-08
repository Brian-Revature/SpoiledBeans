package com.revature.web.controllers;

import com.revature.dtos.FavoritesDTO;
import com.revature.dtos.MoviesDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.dtos.UserDTO;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@RequestParam int id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "/getuserbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

        //TODO: Get User currently signed in to update/set values
    @PutMapping(path = "/update", consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDTO userdto) {
       userService.updateUser(userdto,1);
    }

    //--------------------- Favorites -------------------------------
    //TODO change user id to get current user
    @GetMapping(path= "/myfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(@RequestParam int id) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserFavorites(id);
    }

    @GetMapping(path= "/userfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(@RequestBody UserDTO userdto) {
        return userService.getUserFavorites(userdto.getUsername());
    }

    @PostMapping(path="/addfavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFavoriteMovie(@RequestBody final MoviesDTO moviesdto) {
        userService.addFavorite(moviesdto);
    }

    @DeleteMapping(path = "/deletefavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserFavorite(@RequestBody final MoviesDTO moviesDTO) {
        userService.deleteUserFavorite(moviesDTO);
    }

//--------------------------- Reviews ---------------------------------------------
    @GetMapping(path= "/myreviews",produces= MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestParam int id) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserReviews(id);
    }

    @GetMapping(path= "/userreviews",produces= MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ReviewsDTO getUserReviews(@RequestBody UserDTO userdto) {
        return userService.getUserReviews(userdto.getUsername());
    }

    @GetMapping(path= "/myreviewsbyrating",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getUserReviewsByRating(@RequestParam boolean ascending) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserReviewsRatingOrder(ascending);
    }

    @GetMapping(path= "/myreviewsbytime",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getUserReviewsByTime(@RequestParam boolean ascending) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserReviewsTimeOrder(ascending);
    }



}
