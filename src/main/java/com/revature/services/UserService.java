package com.revature.services;

import com.revature.entities.User;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository repo) {
        super();
        this.userRepo = repo;
    }

    public User getUserById(int id) {
        if (id <= 0 ) {
            throw new InvalidRequestException();
        }
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    //TODO: verify everything is in order when JWT established
    public void save(User u) {
        userRepo.save(u);
    }
}
