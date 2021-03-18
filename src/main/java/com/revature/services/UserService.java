package com.revature.services;

import com.revature.dtos.*;
import com.revature.entities.Movie;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.UserRepository;
import com.revature.util.Encryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

/**
 * Service CLass which handles taking user data from the controller layer and passes it to the repo layer.
 * Also accepts data from the repo layer and passes it to the controller level.
 */
@Service
public class UserService {


    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private final AuthService authService;
    private final UserRepository userRepo;
    private final MovieService movieService;

    @Autowired
    public UserService(final UserRepository repo,final MovieService movieService,final AuthService authService) {
        this.userRepo = repo;
        this.movieService = movieService;
        this.authService = authService;
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

    public User getUserById(final int id) {
        if (id <= 0 ) {
            throw new InvalidRequestException();
        }
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public User getUserByUsername(final String username){
        if(username==null || username.trim().equals("")){
            throw new InvalidRequestException("Username cannot be empty or null");
        }
        return userRepo.findUserByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    public void save(final User u) {
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

    private FavoritesDTO getFavoritesDTO(final User user){
        final FavoritesDTO favs = new FavoritesDTO();
        favs.setUsername(user.getUsername());
        favs.setFavorites(user.getUserFavorites());
        return favs;
    }

    public FavoritesDTO getUserFavorites(final int id){
        return getFavoritesDTO(getUserById(id));
    }

    public FavoritesDTO getUserFavorites(final String username){
        return getFavoritesDTO(getUserByUsername(username));
    }

    public void addFavorite(final MoviesDTO moviesDTO,final int id) {
        movieService.saveNewMovie(moviesDTO);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        final User user = getUserById(id);
        user.addMovieToFavorites(movie);
        userRepo.save(user);
    }

    public void deleteUserFavorite(final MoviesDTO moviesDTO,final int id) {
        final User user = getUserById(id);
        final Movie movie = movieService.getMovieByName(moviesDTO.getName());
        if (!user.getUserFavorites().contains(movie))
            throw new ResourceNotFoundException();

        user.removeMovieFromFavorites(movie);
        userRepo.save(user);
    }

    public PrincipalDTO authenticate(final String username,final String password) {
        final User authUser = userRepo.findUserByUsernameAndPassword(username, Encryption.encrypt(password)).orElseThrow(AuthenticationException::new);
        final PrincipalDTO principal = new PrincipalDTO(authUser);
        final String token = authService.generateToken(principal);
        principal.setToken(token);
        return principal;
    }

    public List<Movie> getUserFavoritesByName(final boolean ascending,final int id) {
        final User user = getUserById(id);
        final List<Movie> movies = user.getUserFavorites();
        final Comparator<Movie> compareByName = (ascending) ?
                (Movie m1, Movie m2) -> m1.getName().compareTo(m2.getName()) :
                (Movie m1, Movie m2) -> m2.getName().compareTo(m1.getName());
        movies.sort(compareByName);
        return movies;
    }

    //-----------------------------------------Utility------------------------------------

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
