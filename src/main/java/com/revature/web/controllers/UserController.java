package com.revature.web.controllers;

import com.revature.dtos.Name;
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
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void setName(@RequestBody Name name){
        User newUser = userService.getUserById(1);
        newUser.setFirstName(name.getFirstName());
        newUser.setLastName(name.getLastName());
        userService.save(newUser);
    }

    //TODO: Get User currently signed in to update/set values
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void setBio(@RequestBody String bio){
        User newUser = userService.getUserById(1);
        newUser.setBio(bio);
        userService.save(newUser);
    }





}
