package com.revature.web.controllers;

import com.revature.entities.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    //TODO: Get User currently signed in to update/set values
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void setName(@RequestParam String firstName, String lastName){
        User newUser = userService.getUserById(1);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        userService.save(newUser);
    }





}
