package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO for retrieving Movie data from OMDb API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDbDTO {

    private String title;
    private int year;
    private String genre;
    private String director;
    private String plot;

    public OMDbDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(final String plot) {
        this.plot = plot;
    }
}
