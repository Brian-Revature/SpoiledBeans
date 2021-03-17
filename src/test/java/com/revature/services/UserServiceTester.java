package com.revature.services;


import com.revature.dtos.FavoritesDTO;
import com.revature.dtos.MoviesDTO;
import com.revature.dtos.PrincipalDTO;
import com.revature.dtos.UserDTO;
import com.revature.entities.Movie;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repos.MovieRepository;
import com.revature.repos.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceTester {

    @Mock
    UserRepository mockUserRepo;
    @Mock
    MovieService mockMovieService;
    @Mock
    AuthService mockAuthService;
    @InjectMocks
    UserService mockUserService;

    public ArrayList<User> users = new ArrayList<User>();
    public User fullUser;
    public User emptyUser;
    public User minUser;
    public UserDTO userDTO;

    public List<Movie> favorites;
    public Movie up;
    public Movie hulk;
    public Movie alien;
    public MoviesDTO moviesDTO;

    @Before
    public void setUp() {
        fullUser = new User("User", "Pass", "ValidGmail@Gmail.com","Brian",
                "Withrow","I want to make movie reviews!" );
        emptyUser = new User();
        minUser = new User("Movie","Maker","AlsoVal@Gmail.com");

        users.add(new User("Rubber","Duckie","RD@gmail.com",
                "Rubby","Ducky","Quack"));
        users.add(new User("Stuffed","Teddy","ST@gmail.com",
                "Stuffy","Teddy","Grrr!"));
        users.add(new User("Stone","Dragon","SD@gmail.com",
                "Rock","Drag","Roaring noise"));
        userDTO = new UserDTO();

        up = new Movie();
        up.setId(1);
        up.setName("Up");
        up.setDirector("UpDirector");
        up.setGenre("Family Adventure");
        up.setSynopsis("Old man and kid go on a high stakes adventure.");
        up.setYear(2010);

        alien = new Movie();
        alien.setId(2);
        alien.setName("Alien");
        alien.setDirector("AlienDirector");
        alien.setGenre("Horror Adventure");
        alien.setSynopsis("Lady kills alien.");
        alien.setYear(1980);

        hulk = new Movie();
        hulk.setId(3);
        hulk.setName("Hulk");
        hulk.setDirector("HulkDirector");
        hulk.setGenre("Superhero Adventure");
        hulk.setSynopsis("Big green guy goes smash");
        hulk.setYear(2000);

        favorites = new ArrayList<>();
        favorites.add(hulk);
        favorites.add(alien);
        favorites.add(up);

        moviesDTO = new MoviesDTO();

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown(){
        users.clear();
    }

    //------------------------------------------------registerNewUser---------------------------------------------------

    @Test
    public void newUserRegisterValid(){
        //arrange
        userDTO.setUsername(fullUser.getUsername());
        userDTO.setPassword(fullUser.getPassword());
        userDTO.setBio(fullUser.getBio());
        userDTO.setEmail(fullUser.getEmail());
        userDTO.setFirstName(fullUser.getFirstName());
        userDTO.setLastName(fullUser.getLastName());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    @Test(expected = InvalidRequestException.class)
    public void newUserRegisterUserInvalid(){
        //arrange
        //userDTO.setUsername(fullUser.getUsername());
        userDTO.setPassword(fullUser.getPassword());
        userDTO.setBio(fullUser.getBio());
        userDTO.setEmail(fullUser.getEmail());
        userDTO.setFirstName(fullUser.getFirstName());
        userDTO.setLastName(fullUser.getLastName());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    @Test(expected = InvalidRequestException.class)
    public void newUserRegisterPassInvalid(){
        //arrange
        userDTO.setUsername(fullUser.getUsername());
        //userDTO.setPassword(fullUser.getPassword());
        userDTO.setBio(fullUser.getBio());
        userDTO.setEmail(fullUser.getEmail());
        userDTO.setFirstName(fullUser.getFirstName());
        userDTO.setLastName(fullUser.getLastName());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    @Test(expected = InvalidRequestException.class)
    public void newUserRegisterEmailInvalid(){
        //arrange
        userDTO.setUsername(fullUser.getUsername());
        userDTO.setPassword(fullUser.getPassword());
        userDTO.setBio(fullUser.getBio());
        //userDTO.setEmail(fullUser.getEmail());
        userDTO.setFirstName(fullUser.getFirstName());
        userDTO.setLastName(fullUser.getLastName());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    @Test
    public void newUserBareMin(){
        //arrange
        userDTO.setUsername(minUser.getUsername());
        userDTO.setPassword(minUser.getPassword());
        userDTO.setEmail(minUser.getEmail());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    @Test(expected = InvalidRequestException.class)
    public void newUserEmpty(){
        //arrange
        userDTO.setUsername(emptyUser.getUsername());
        userDTO.setPassword(emptyUser.getPassword());
        userDTO.setEmail(emptyUser.getEmail());

        //Act
        mockUserService.registerNewUser(userDTO);
    }

    //------------------------------------------------getUserById-------------------------------------------------------

    @Test
    public void getUserFull(){
        //arrange
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));

        //Act
        User user = mockUserService.getUserById(1);

        //Assert
        Assert.assertEquals(user, fullUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void getUserInvalid(){
        //Act
        mockUserService.getUserById(0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserNoneFound(){
        //Arrange
        when(mockUserRepo.findById(1)).thenReturn(Optional.empty());

        //Act
        User user = mockUserService.getUserById(1);
    }

    //--------------------------------------------getUserByUsername-----------------------------------------------------

    @Test
    public void getUserFullUser(){
        //Arrange
        when(mockUserRepo.findUserByUsername("User")).thenReturn(Optional.of(fullUser));
        String username = fullUser.getUsername();

        //Act
        User user = mockUserService.getUserByUsername(username);

        //Arrange
        Assert.assertEquals(user, fullUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void getUserInvalidUsername(){
        //Act
        mockUserService.getUserByUsername("");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserNoneFoundUsername(){
        //Arrange
        when(mockUserRepo.findUserByUsername("User")).thenReturn(Optional.empty());

        //Act
        User user = mockUserService.getUserByUsername(fullUser.getUsername());
    }

    //----------------------------------------------getUserFavs---------------------------------------------------------

    @Test
    public void validUserFavsList(){
        //Arrange
        fullUser.addMovieToFavorites(up);
        fullUser.addMovieToFavorites(hulk);
        fullUser.addMovieToFavorites(alien);
        when(mockUserRepo.findUserByUsername(fullUser.getUsername())).thenReturn(Optional.of(fullUser));

        //Act
        FavoritesDTO favs = mockUserService.getUserFavorites(fullUser.getUsername());

        //Assert
        Assert.assertEquals(favs.getFavorites(),fullUser.getUserFavorites());
    }

    @Test
    public void validUserFavsListById(){
        //Arrange
        fullUser.addMovieToFavorites(up);
        fullUser.addMovieToFavorites(hulk);
        fullUser.addMovieToFavorites(alien);
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));

        //Act
        FavoritesDTO favs = mockUserService.getUserFavorites(1);

        //Assert
        Assert.assertEquals(favs.getFavorites(),fullUser.getUserFavorites());
    }

    //----------------------------------------------AddFav--------------------------------------------------------------

    @Test
    public void addValidFavorite(){
        //Arrange
        moviesDTO.setName(up.getName());
        moviesDTO.setDirector(up.getDirector());
        moviesDTO.setGenre(up.getDirector());
        moviesDTO.setSynopsis(up.getSynopsis());
        when(mockMovieService.saveNewMovie(moviesDTO)).thenReturn(true);
        when(mockMovieService.getMovieByName(up.getName())).thenReturn(up);
        when(mockUserRepo.save(fullUser)).thenReturn(fullUser);
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));

        //Act
        mockUserService.addFavorite(moviesDTO, 1);

        //Assert
        Assert.assertEquals(fullUser.getUserFavorites().contains(up),true);
    }

    //----------------------------------------------DeleteFav-----------------------------------------------------------

    @Test
    public void deleteValidFavorite(){
        //Arrange
        moviesDTO.setName(up.getName());
        moviesDTO.setDirector(up.getDirector());
        moviesDTO.setGenre(up.getDirector());
        moviesDTO.setSynopsis(up.getSynopsis());
        when(mockMovieService.saveNewMovie(moviesDTO)).thenReturn(true);
        when(mockMovieService.getMovieByName(up.getName())).thenReturn(up);
        when(mockUserRepo.save(fullUser)).thenReturn(fullUser);
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));

        //Act
        fullUser.addMovieToFavorites(up);
        mockUserService.deleteUserFavorite(moviesDTO,1);

        //Assert
        Assert.assertEquals(fullUser.getUserFavorites().contains(up),false);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteInvalidFavorite(){
        //Arrange
        moviesDTO.setName(up.getName());
        moviesDTO.setDirector(up.getDirector());
        moviesDTO.setGenre(up.getDirector());
        moviesDTO.setSynopsis(up.getSynopsis());
        when(mockMovieService.saveNewMovie(moviesDTO)).thenReturn(true);
        when(mockMovieService.getMovieByName(up.getName())).thenReturn(up);
        when(mockUserRepo.save(fullUser)).thenReturn(fullUser);
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));

        //Act
        fullUser.addMovieToFavorites(hulk);
        mockUserService.deleteUserFavorite(moviesDTO,1);

        //Assert
        Assert.assertEquals(fullUser.getUserFavorites().contains(up),false);
    }

    //-------------------------------------------Authenticate-----------------------------------------------------------

    @Test
    public void validAuthorization(){
        //Arrange
        fullUser.setUserRole(UserRole.BASIC_USER);
        fullUser.setId(1);
        PrincipalDTO fullUserPrinciple = new PrincipalDTO(fullUser);
        when(mockUserRepo.findUserByUsernameAndPassword(fullUser.getUsername(),
                fullUser.getPassword())).thenReturn(Optional.of(fullUser));
        when(mockAuthService.generateToken(fullUserPrinciple)).thenReturn("Token");

        //Act
        PrincipalDTO principalDTO = mockUserService.authenticate("User","Pass");

        //Assert
        Assert.assertEquals("Token",principalDTO.getToken());
    }

    @Test(expected = AuthenticationException.class)
    public void invalidAuthorization(){
        //Arrange
        when(mockUserRepo.findUserByUsernameAndPassword(fullUser.getUsername(),
                fullUser.getPassword())).thenReturn(Optional.empty());

        //Act
        PrincipalDTO principalDTO = mockUserService.authenticate("User","Pass");
    }

    //-------------------------------------------getFavByName-----------------------------------------------------------

    @Test
    public void getFavByNameAsc(){
        //Arrange
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));
        List<Movie> orderedList = new ArrayList<>();
        orderedList.add(alien);
        orderedList.add(hulk);
        orderedList.add(up);
        fullUser.addMovieToFavorites(up);
        fullUser.addMovieToFavorites(hulk);
        fullUser.addMovieToFavorites(alien);

        //Act
        List<Movie> fullUserOrganized = mockUserService.getUserFavoritesByName(true,1);

        //Assert
        Assert.assertEquals(orderedList,fullUserOrganized);
    }

    @Test
    public void getFavByNameDesc(){
        //Arrange
        when(mockUserRepo.findById(1)).thenReturn(Optional.of(fullUser));
        List<Movie> orderedList = new ArrayList<>();
        orderedList.add(up);
        orderedList.add(hulk);
        orderedList.add(alien);
        fullUser.addMovieToFavorites(hulk);
        fullUser.addMovieToFavorites(up);
        fullUser.addMovieToFavorites(alien);

        //Act
        List<Movie> fullUserOrganized = mockUserService.getUserFavoritesByName(false,1);

        //Assert
        Assert.assertEquals(orderedList,fullUserOrganized);
    }

    //-------------------------------------------getFavByName-----------------------------------------------------------
}
