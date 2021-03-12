package com.revature.web.controllers;

import com.revature.dtos.*;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.exceptions.AuthenticationException;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * An endpoint that controls interactions with user and a user's list of favorite movies
 */
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Returns the user that is currently logged based on user id obtained from JWT
     * @param request
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(HttpServletRequest request) {
        return userService.getUserById(authService.getUserId(getToken(request)));
    }

    /**
     * Returns the user that matches the supplied username
     * @param username
     * @return
     */
    //TODO Change the parameter to a @requestBody and pass it a UserDTO
    @GetMapping(path = "/getuserbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Updates any values that are present in the UserDTO
     * @param userdto
     * @param request
     */
    @PutMapping(path = "/update", consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDTO userdto, HttpServletRequest request) {
        userService.updateUser(userdto, authService.getUserId(getToken(request)));
    }

    //--------------------- Favorites -------------------------------

    /**
     * Returns a list of the currently signed in user's favorite movies
     * @param request
     * @return
     */
    @GetMapping(path= "/myfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(HttpServletRequest request) {
        return userService.getUserFavorites(authService.getUserId(getToken(request)));
    }

    /**
     * Returns a list of a user's favorite movies based on the supplied username
     * @param userdto
     * @return
     */
    @GetMapping(path= "/userfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(@RequestBody UserDTO userdto) {
        return userService.getUserFavorites(userdto.getUsername());
    }

    /**
     * Adds a favorite movies to the currently logged in user based on user id obtained from JWT
     * @param moviesdto
     * @param request
     */
    @PostMapping(path="/addfavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFavoriteMovie(@RequestBody final MoviesDTO moviesdto, HttpServletRequest request) {
        userService.addFavorite(moviesdto, authService.getUserId(getToken(request)));
    }

    /**
     * Deletes a movie from the currently logged in user's favorites list
     * @param moviesDTO
     * @param request
     */
    @DeleteMapping(path = "/deletefavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserFavorite(@RequestBody final MoviesDTO moviesDTO, HttpServletRequest request) {
        userService.deleteUserFavorite(moviesDTO, authService.getUserId(getToken(request)));
    }

    /**
     * Returns a user's favorite movies list ordered by movie name
     * @param ascending if true, list is ordered ascending. If false, list is ordered descending
     * @param request
     * @return
     */
    @GetMapping(path= "/favoritesbyname",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getUserFavoritesByName(@RequestParam boolean ascending, HttpServletRequest request) {
        return userService.getUserFavoritesByName(ascending, authService.getUserId(getToken(request)));
    }

    //----------------------------------------------------------------------------
    // Helper method to process a user's cookies
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
