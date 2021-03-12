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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(HttpServletRequest request) {
        return userService.getUserById(authService.getUserId(getToken(request)));
    }

    @GetMapping(path = "/getuserbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }


    @PutMapping(path = "/update", consumes =  MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody UserDTO userdto, HttpServletRequest request) {
        userService.updateUser(userdto, authService.getUserId(getToken(request)));
    }

    //--------------------- Favorites -------------------------------
    @GetMapping(path= "/myfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(HttpServletRequest request) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserFavorites(authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/userfavorites",produces= MediaType.APPLICATION_JSON_VALUE)
    public FavoritesDTO getUserFavorites(@RequestBody UserDTO userdto) {
        return userService.getUserFavorites(userdto.getUsername());
    }

    @PostMapping(path="/addfavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addFavoriteMovie(@RequestBody final MoviesDTO moviesdto, HttpServletRequest request) {
        userService.addFavorite(moviesdto, authService.getUserId(getToken(request)));
    }

    @DeleteMapping(path = "/deletefavorite",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserFavorite(@RequestBody final MoviesDTO moviesDTO, HttpServletRequest request) {
        userService.deleteUserFavorite(moviesDTO, authService.getUserId(getToken(request)));
    }

    @GetMapping(path= "/favoritesbyname",produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getUserFavoritesByName(@RequestParam boolean ascending, HttpServletRequest request) {
        //Not clear how we are getting the user id or user data at this point
        return userService.getUserFavoritesByName(ascending, authService.getUserId(getToken(request)));
    }

    //----------------------------------------------------------------------------

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
