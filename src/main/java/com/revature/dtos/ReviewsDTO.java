package com.revature.dtos;

import com.revature.entities.Review;

import java.util.List;
import java.util.Objects;

public class ReviewsDTO {

    private String username;
    private List<Review> reviews;

    public ReviewsDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewsDTO that = (ReviewsDTO) o;
        return username.equals(that.username) && Objects.equals(reviews, that.reviews);
    }

    @Override
    public String toString() {
        return "ReviewsDTO{" +
                "username='" + username + '\'' +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, reviews);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}