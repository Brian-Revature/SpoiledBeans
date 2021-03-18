package com.revature.web.controllers;

import com.revature.dtos.*;
import com.revature.entities.Movie;
import com.revature.entities.User;
import com.revature.exceptions.AuthenticationException;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * There once was an ity bitty little Java Class who dreamed of growing up one day to be a big important Class.
 * He went to Java school every day and was made fun of by the other Java Classes. They teased and bullied him, telling him
 * that he will never grow up to be a big important Class. This didn't stop the ity bitty little Java Class, he Kept going
 * to Java school and studied hard. He never gave up on his dream of being a big important Java CLass one day.
 * Eventually he graduated from JAva School and got a job working for SpoiledBeans where he was put in a position to be
 * an Important Controller class. He was put in charge of handling User request to the website.
 * This once ity bitty little Java CLass is now a big important Java Class who can take request to get user information
 * from the site, or send user information to the site. He can also get a User's favorites from the site and send data back to
 * the website form the end user.  All of his old classmates now look upon that once ity bitty little Java Class with respect now.
 * He truly became a big important Java Class.
 */
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(final UserService userService,final AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Returns the user that is currently logged based on user id obtained from JWT
     * @param request
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(final HttpServletRequest request) {
        return userService.getUserById(authService.getUserId(getToken(request)));
    }

    /**
     * Returns the user that matches the supplied username
     * @param username
     * @return
     */
    @GetMapping(path = "/getuserbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@RequestParam final String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Updates any values that are present in the UserDTO
     * @param userdto
     * @param request
     */
    @PutMapping(path = "/update", consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody final UserDTO userdto,final HttpServletRequest request) {
        userService.updateUser(userdto, authService.getUserId(getToken(request)));
    }

    //--------------------- Favorites -------------------------------

    /**
     * Returns a list of the currently signed in user's favorite movies
     * @param request
     * @return
     */
    @GetMapping(path= "/myfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(final HttpServletRequest request) {
        return userService.getUserFavorites(authService.getUserId(getToken(request)));
    }

    /**
     * Returns a list of a user's favorite movies based on the supplied username
     * @param username
     * @return
     */
    @GetMapping(path= "/userfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(@RequestParam final String username) {
        return userService.getUserFavorites(username);
    }

    /**
     * Adds a favorite movies to the currently logged in user based on user id obtained from JWT
     * @param moviesdto
     * @param request
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/addfavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFavoriteMovie(@RequestBody final MoviesDTO moviesdto,final HttpServletRequest request) {
        userService.addFavorite(moviesdto, authService.getUserId(getToken(request)));
    }

    /**
     * Deletes a movie from the currently logged in user's favorites list
     * @param moviesDTO
     * @param request
     */
    @DeleteMapping(path = "/deletefavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserFavorite(@RequestBody final MoviesDTO moviesDTO,final HttpServletRequest request) {
        userService.deleteUserFavorite(moviesDTO, authService.getUserId(getToken(request)));
    }

    /**
     * Returns a user's favorite movies list ordered by movie name
     * @param ascending if true, list is ordered ascending. If false, list is ordered descending
     * @param request
     * @return
     */
    @GetMapping(path= "/favoritesbyname",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getUserFavoritesByName(@RequestParam boolean ascending,final HttpServletRequest request) {
        return userService.getUserFavoritesByName(ascending, authService.getUserId(getToken(request)));
    }

    //----------------------------------------------------------------------------
    // Helper method to process a user's cookies
    private String getToken(final HttpServletRequest request){
        final String token = request.getHeader("spoiledBeans-token");
        if(token.trim().equals("")){
            throw new AuthenticationException("You are not an authenticated account");
        }
        return token;
    }

}
