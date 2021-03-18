package com.revature.web.controllers;

import com.revature.entities.User;
import com.revature.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerTester {

    @MockBean
    private UserRepository mockUserRepo;

    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public ArrayList<User> users;
    public User user;

    @Autowired
    public UserControllerTester(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void startUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        users = new ArrayList<>();
        user = new User("Stone","Dragon","SD@gmail.com",
                "Rock","Drag","Roaring noise");
        users.add(user);
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        when(mockUserRepo.findUserByUsername("Stone")).thenReturn(Optional.of(user));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/getuserbyusername").param("username","Stone"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.username").value(user.getUsername()));
    }
}
