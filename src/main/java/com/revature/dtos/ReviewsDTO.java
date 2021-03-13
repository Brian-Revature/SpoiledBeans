package com.revature.dtos;

import com.revature.entities.Movie;
import com.revature.entities.Review;
import com.revature.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewsDTO {

    private String username;
    private String movie;
    private List<Movie> movies;
    private List<Review> reviews;
    private List <User> users;

    public ReviewsDTO() {
        movies = new ArrayList<>();
        reviews = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewsDTO that = (ReviewsDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(movie, that.movie) && Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, movie, reviews);
    }

    @Override
    public String toString() {
        return "ReviewsDTO{" +
                "username='" + username + '\'' +
                ", movie='" + movie + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
