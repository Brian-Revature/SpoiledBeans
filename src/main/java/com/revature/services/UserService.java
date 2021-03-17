package com.revature.services;

import com.revature.dtos.*;
import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.UserRepository;
import com.revature.util.Encryption;
import com.revature.web.intercom.OMDbClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private final AuthService authService;
    private final UserRepository userRepo;
    private final MovieService movieService;
    private final OMDbClient omdb;

    @Autowired
    public UserService(UserRepository repo, MovieService movieService, AuthService authService, OMDbClient omdb) {
        this.userRepo = repo;
        this.movieService = movieService;
        this.authService = authService;
        this.omdb = omdb;
    }

    //----------------------------------Users----------------------------------------------

    public void registerNewUser(final UserDTO userdto) {
        final User user = new User();
        userdto.setUserRole(UserRole.BASIC_USER.toString());
        mapUserFromDTO(user,userdto);
        user.setUserRole(UserRole.BASIC_USER);
        userValid(userdto);

        userRepo.save(user);
        System.out.println(user);
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

    public void save(User u) {
        userRepo.save(u);
    }

    public void updateUser(final UserDTO userdto, final int user_id) {
        final User user = getUserById(user_id);
        mapUserFromDTO(user,userdto);
        userRepo.save(user);
    }

    public void updateUser(final User user) {
        userRepo.save(user);
    }

    //----------------------------------------Favorites---------------------------------------

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

    public void addFavorite(final MoviesDTO moviesDTO, int id) {
        movieService.saveNewMovie(moviesDTO);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        final User user = getUserById(id);
        user.addMovieToFavorites(movie);
        userRepo.save(user);
    }

    public void deleteUserFavorite(final MoviesDTO moviesDTO, int id) {
        final User user = getUserById(id);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        if (!user.getUserFavorites().contains(movie))
            throw new ResourceNotFoundException();

        user.removeMovieFromFavorites(movie);
        userRepo.save(user);
    }

    public PrincipalDTO authenticate(String username, String password) {
        User authUser = userRepo.findUserByUsernameAndPassword(username, Encryption.encrypt(password)).orElseThrow(AuthenticationException::new);
        PrincipalDTO principal = new PrincipalDTO(authUser);
        String token = authService.generateToken(principal);
        principal.setToken(token);
        return principal;
    }

    public List<Movie> getUserFavoritesByName(boolean ascending, int id) {
        final User user = getUserById(id);
        List<Movie> movies = user.getUserFavorites();
        Comparator<Movie> compareByName = (ascending) ?
                (Movie m1, Movie m2) -> m1.getName().compareTo(m2.getName()) :
                (Movie m1, Movie m2) -> m2.getName().compareTo(m1.getName());
        Collections.sort(movies,compareByName);
        return movies;
    }

    //-----------------------------------------Utility------------------------------------

    //TODO: Replace Basic User functionality with a check for the given role.
    private void mapUserFromDTO(final User user, final UserDTO userdto) {
        if(userdto.getUsername() != null && !userdto.getUsername().trim().equals(""))
            user.setUsername(userdto.getUsername());
        if(userdto.getPassword() != null && !userdto.getPassword().trim().equals(""))
            user.setPassword(Encryption.encrypt(userdto.getPassword()));
        if(userdto.getFirstName() != null && !userdto.getFirstName().trim().equals(""))
            user.setFirstName(userdto.getFirstName());
        if(userdto.getLastName() != null && !userdto.getLastName().trim().equals(""))
            user.setLastName(userdto.getLastName());
        if(userdto.getEmail() != null && !userdto.getEmail().trim().equals(""))
            user.setEmail(userdto.getEmail());
        if(userdto.getBio() != null && !userdto.getBio().trim().equals(""))
            user.setBio(userdto.getBio());
        if(userdto.getUserRole() != null && !userdto.getUserRole().trim().equals(""))
            user.setUserRole(UserRole.BASIC_USER);
    }

    private void userValid(final UserDTO userDTO){
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().equals(""))
            throw new InvalidRequestException();
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().equals(""))
            throw new InvalidRequestException();
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().equals(""))
            throw new InvalidRequestException();
    }
}
