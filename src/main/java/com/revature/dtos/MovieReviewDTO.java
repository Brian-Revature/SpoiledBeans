package com.revature.dtos;

import com.revature.entities.Movie;
import com.revature.entities.Review;

import java.util.Objects;

public class MovieReviewDTO {

    private Movie movie;
    private Review review;

    public MovieReviewDTO() {
    }

    public MovieReviewDTO(Movie movie, Review review) {
        this.movie = movie;
        this.review = review;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieReviewDTO that = (MovieReviewDTO) o;
        return Objects.equals(movie, that.movie) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, review);
    }

    @Override
    public String toString() {
        return "MovieReviewDTO{" +
                "movie=" + movie +
                ", review=" + review +
                '}';
    }
}
