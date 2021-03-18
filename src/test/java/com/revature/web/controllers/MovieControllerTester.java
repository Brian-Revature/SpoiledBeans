package com.revature.web.controllers;

import com.revature.entities.Movie;
import com.revature.repos.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MovieControllerTester {

    @MockBean
    private MovieRepository mockMovieRepo;

    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public ArrayList<Movie> arr;
    public Movie hotRod;

    @Autowired
    public MovieControllerTester(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void startUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        arr = new ArrayList<Movie>();
        hotRod = new Movie();
        hotRod.setId(1);
        hotRod.setName("Hot Rod");
        hotRod.setDirector("Akiva Schaffer");
        hotRod.setGenre("Comedy");
        hotRod.setSynopsis("Rod Kimble!");
        hotRod.setYear(2007);
        arr.add(hotRod);
    }

    @Test
    public void testGetAllMovies() throws Exception {
        when(mockMovieRepo.findAll()).thenReturn(arr);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(arr.size()));
    }

    @Test
    public void testGetMovieById() throws Exception {
        when(mockMovieRepo.findById(1)).thenReturn(Optional.of(hotRod));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/id").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(hotRod.getId()));
    }

    @Test
    public void testGetMoviesByYear() throws Exception {
        when(mockMovieRepo.findMoviesByYear(2007)).thenReturn(arr);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/year").param("year", "2007"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(arr.size()));
    }

    @Test
    public void testGetMovieByName() throws Exception {
        when(mockMovieRepo.findMovieByName("Hot Rod")).thenReturn(Optional.of(hotRod));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/name").param("name", "Hot Rod"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(hotRod.getName()));
    }

    @Test
    public void testGetMoviesByGenre() throws Exception {
        when(mockMovieRepo.findMoviesByGenre("Comedy")).thenReturn(arr);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/genre").param("genre", "Comedy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(arr.size()));
    }

    @Test
    public void testGetMoviesByDirector() throws Exception {
        when(mockMovieRepo.findMoviesByDirector("Akiva Schaffer")).thenReturn(arr);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/director").param("director", "Akiva Schaffer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(arr.size()));
    }
}
