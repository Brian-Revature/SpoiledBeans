package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Table(name = "movies")
public class Movie {

    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String director;

    @Column
    private String genre;

    @Column
    private String synopsis;

    @Column(name = "release_date", nullable = false)
    private int year;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_review",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> allReviews;

    @JsonInclude
    @Transient
    private double rating;

    public Movie(){
        super();
    }

    public Movie(String name, String director, int year) {
        this.name = name;
        this.director = director;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addReview(final Review review){
        if (allReviews == null) {
            allReviews = new ArrayList<>();
        }
        allReviews.add(review);
    }

    public void removeReview(final Review review) { allReviews.remove(review); }

    public List<Review> getAllReviews() {
        return allReviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && year == movie.year && Objects.equals(name, movie.name) && Objects.equals(director, movie.director) && Objects.equals(genre, movie.genre) && Objects.equals(synopsis, movie.synopsis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, director, genre, synopsis, year);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", year=" + year +
                '}';
    }
}
