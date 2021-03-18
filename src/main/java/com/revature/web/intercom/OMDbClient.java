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

/**
 * This class handles sending requests to the foreign API that we are leveraging in our
 * application
 */
@Component
public class OMDbClient {

    private final RestTemplate restClient;

    /**
     * Constructor that needs a rest template
     * @param restClient the rest template leveraged to call foreign apis
     */
    @Autowired
    public OMDbClient(final RestTemplate restClient){
        this.restClient = restClient;
    }

    /**
     * This method takes a movie name as a string, and grabs it from the foreign api
     * @param name the name of the movie
     * @return the movie object built from the response from the api
     */
    public Movie getMovieInformation(final String name){
        final StringBuilder movieUrl = new StringBuilder("http://www.omdbapi.com/?apikey=9235369b&t=");
        final String[] movieNames = name.split(" ");

        for(int i = 0; i < movieNames.length; i++){
            if(i == movieNames.length-1){
                movieUrl.append(movieNames[i]);
                break;
            }
            movieUrl.append(movieNames[i]).append("+");
        }
        return parseOMDbBody(restClient.exchange(movieUrl.toString(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class).getBody());
    }

    /**
     * Parses the OMDb response into a movie object
     * @param body the body of the response from OMDb
     * @return the movie object
     */
    private Movie parseOMDbBody(String body){
        body = body.replace("Title", "title");
        body = body.replace("Year", "year");
        body = body.replace("Genre", "genre");
        body = body.replace("Plot", "plot");
        body = body.replace("Director", "director");
        final ObjectMapper mapper = new ObjectMapper();
        final Movie m = new Movie();
        OMDbDTO o = new OMDbDTO();
        try {
            o = mapper.readValue(body, OMDbDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final String[] genres = o.getGenre().split(",");

        m.setName(o.getTitle());
        m.setDirector(o.getDirector());
        m.setYear(o.getYear());
        m.setGenre(genres[0]);
        m.setSynopsis(o.getPlot());

        return m;
    }
}
