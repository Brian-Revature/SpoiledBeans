package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @DynamicInsert
@Table(name = "review")
public class Review {

    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "NUMERIC(2,1)")
    private double rating;

    @Column
    private String review;

//    @Generated(value = GenerationTime.ALWAYS)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Column(name = "review_time", nullable = false)
    private Timestamp reviewTime;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_review",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> allMovieReviews;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_reviews",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> allReviewers;

    public Review(){
        super();
        allMovieReviews = new ArrayList<>();
        allReviewers = new ArrayList<>();
    }

    public Review(double rating, String review) {
        this.rating = rating;
        this.review = review;
        allMovieReviews = new ArrayList<>();
        allReviewers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    public void addReviewer(final User user){
        if (allReviewers == null) {
            allReviewers = new ArrayList<>();
        }
        allReviewers.add(user);
    }

    public void removeMovieReview(final Movie movie) { allMovieReviews.remove(movie); }

    public List<Movie> GetAllMovieReviews() {
        return allMovieReviews;
    }

    public void addMovieReview(final Movie movie){
        if (allMovieReviews == null) {
            allMovieReviews = new ArrayList<>();
        }
        allMovieReviews.add(movie);
    }

    public void removeReviewer(final User user) { allReviewers.remove(user); }

    public List<User> getAllReviewers() {
        return allReviewers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return id == review1.id && Double.compare(review1.rating, rating) == 0 && Objects.equals(review, review1.review) && Objects.equals(reviewTime, review1.reviewTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, review, reviewTime);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", reviewTime=" + reviewTime +
                '}';
    }
}
