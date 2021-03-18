package com.revature.services;

import com.revature.dtos.PrincipalDTO;
import com.revature.entities.User;
import com.revature.entities.UserRole;
import com.revature.util.JwtGenerator;
import com.revature.util.JwtValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class AuthServiceTester {

    @Mock
    public JwtGenerator mockTokenGenerator;
    @Mock
    public JwtValidator mockTokenValidator;
    @InjectMocks
    public AuthService mockAuthService;

    public PrincipalDTO principalDTO;
    public User fullUser;

    @Before
    public void setUp(){
        fullUser = new User("User", "Pass", "ValidGmail@Gmail.com","Brian",
                "Withrow","I want to make movie reviews!" );
        fullUser.setId(1);
        fullUser.setUserRole(UserRole.BASIC_USER);
        principalDTO = new PrincipalDTO(fullUser);
        MockitoAnnotations.initMocks(this);
    }

    //------------------------------------------------generateToken-----------------------------------------------------

    @Test
    public void successTokenGenerate(){
        //Arrange
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn("Token");

        //Act
        String token = mockAuthService.generateToken(principalDTO);

        //Assert
        Assert.assertEquals("Token",token);
    }

    //------------------------------------------------generateToken-----------------------------------------------------

    @Test
    public void tokenValid(){
        //Arrange
        when(mockTokenValidator.parseToken("Token")).thenReturn(principalDTO);
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn("Token");
        String token = mockAuthService.generateToken(principalDTO);

        //Act
        boolean validPass = mockAuthService.isTokenValid(token);

        //Assert
        Assert.assertEquals(true,validPass);
    }

    @Test
    public void tokenInvalid(){
        //Arrange
        when(mockTokenValidator.parseToken("Token")).thenReturn(principalDTO);
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn(null);
        String token = mockAuthService.generateToken(principalDTO);

        //Act
        boolean validPass = mockAuthService.isTokenValid(token);

        //Assert
        Assert.assertEquals(false,validPass);
    }

    //-----------------------------------------------getAuthorities-----------------------------------------------------

    @Test
    public void checkBasic(){
        //Arrange
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn("Token");
        when(mockTokenValidator.parseToken("Token")).thenReturn(principalDTO);

        //Act
        String role = mockAuthService.getAuthorities("Token");

        //Assert
        Assert.assertEquals("Basic User", role);

    }

    @Test(expected = RuntimeException.class)
    public void invalidUser(){
        //Arrange
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn("Token");
        when(mockTokenValidator.parseToken("Token")).thenReturn(null);

        //Act
        String role = mockAuthService.getAuthorities("Token");
    }

    //-----------------------------------------------getUserId-----------------------------------------------------

    @Test
    public void checkId(){
        //Arrange
        when(mockTokenGenerator.createToken(principalDTO)).thenReturn("Token");
        when(mockTokenValidator.parseToken("Token")).thenReturn(principalDTO);

        //Act
        int id = mockAuthService.getUserId("Token");

        //Assert
        Assert.assertEquals(1, id);

    }
}
