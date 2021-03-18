package com.revature.dtos;

import com.revature.entities.Movie;
import com.revature.entities.Review;

import java.util.Objects;


/**
 * A DTO for transferring movie reviews between client and backend.
 */
public class MovieReviewDTO {

    private Movie movie;
    private Review review;

    public MovieReviewDTO() {
    }

    public MovieReviewDTO(final Movie movie, final Review review) {
        this.movie = movie;
        this.review = review;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(final Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MovieReviewDTO that = (MovieReviewDTO) o;
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
