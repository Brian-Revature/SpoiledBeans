package com.revature.web.controllers;

import com.revature.dtos.Credentials;
import com.revature.dtos.PrincipalDTO;
import com.revature.dtos.UserDTO;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/registeruser", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewUser(@RequestBody final UserDTO userdto) {
        userService.registerNewUser(userdto);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PrincipalDTO authenticateUser(@RequestBody @Valid Credentials credentials, HttpServletResponse response) {
        PrincipalDTO principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        Cookie cookie = new Cookie("spoiledBeans-token", principal.getToken());
        cookie.setPath("/");
        response.addCookie(cookie);
        return principal;
    }

}
