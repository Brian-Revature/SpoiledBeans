package com.revature.services;

import com.revature.dtos.FavoritesDTO;
import com.revature.dtos.ReviewsDTO;
import com.revature.dtos.UserDTO;
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


}
