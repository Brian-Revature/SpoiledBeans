package com.revature.dtos;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Data transfer Object to facilitate sending movies between client and backend.
 */
public class MoviesDTO {

    private String name;
    private String director;
    private String genre;
    private String synopsis;
    private LocalDate year;



    public MoviesDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesDTO moviesDTO = (MoviesDTO) o;
        return Objects.equals(name, moviesDTO.name) && Objects.equals(director, moviesDTO.director) && Objects.equals(genre, moviesDTO.genre) && Objects.equals(synopsis, moviesDTO.synopsis) && Objects.equals(year, moviesDTO.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, director, genre, synopsis, year);
    }

    @Override
    public String toString() {
        return "MoviesDTO{" +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", year=" + year +
                '}';
    }
}
