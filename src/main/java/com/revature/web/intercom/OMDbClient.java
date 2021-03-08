package com.revature.web.intercom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.OMDbDTO;
import com.revature.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OMDbClient {

    private RestTemplate restClient;

    @Autowired
    public OMDbClient(RestTemplate restClient){
        this.restClient = restClient;
    }

    public Movie getMovieInformation(String name){
        String movieUrl = "http://www.omdbapi.com/?apikey=9235369b&t=";
        String[] movieNames = name.split(" ");

        for(int i = 0; i < movieNames.length; i++){
            if(i == movieNames.length-1){
                movieUrl += movieNames[i];
                break;
            }
            movieUrl += movieNames[i] + "+";
        }
        return parseOMDbBody(restClient.exchange(movieUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class).getBody());
    }

    //TODO synopsis only grabs the first 20 characters, will need to change the DB side of things for longer plot
    private Movie parseOMDbBody(String body){
        body = body.replace("Title", "title");
        body = body.replace("Year", "year");
        body = body.replace("Genre", "genre");
        body = body.replace("Plot", "plot");
        body = body.replace("Director", "director");
        ObjectMapper mapper = new ObjectMapper();
        Movie m = new Movie();
        OMDbDTO o = new OMDbDTO();
        try {
            o = mapper.readValue(body, OMDbDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String[] genres = o.getGenre().split(",");
        String synopsis = o.getPlot().substring(0,20);

        m.setName(o.getTitle());
        m.setDirector(o.getDirector());
        m.setYear(o.getYear());
        m.setGenre(genres[0]);
        m.setSynopsis(synopsis);


        return m;
    }


}
