package com.revature.web.controllers;

import com.revature.dtos.Credentials;
import com.revature.dtos.PrincipalDTO;
import com.revature.dtos.UserDTO;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller class which handles User login and registering new user.
 * User can log in, and upon successfully logging in they are given a JWT.
 * USer can also register a new account.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to Register a new User for the site. takes in a UserDTO and construct a new user in the database form the provided DTO.
     * @param userdto DTO which contains the data for the new User to create.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/registeruser", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewUser(@RequestBody final UserDTO userdto) {
        userService.registerNewUser(userdto);
    }

    /**
     * Method to Log in to the site using an already registered user.
     * @param credentials DTO which contains the User's log in credentials.
     * @param response HTTP response object which contains the generate token for the user.
     * @return DTO which contains User's log in After successfully logging in.
     */
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PrincipalDTO authenticateUser(@RequestBody @Valid final Credentials credentials, final HttpServletResponse response) {
        final PrincipalDTO principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        response.addHeader("spoiledBeans-token", principal.getToken());
        return principal;
    }

}
