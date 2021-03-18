package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Class to represent Movie reviews stored inside of database.
 */
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_review",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Movie movie;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_reviews",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User reviewer;

    public Review(){
        super();
    }

    public Review(final double rating,final String review,final Movie movie,final User reviewer) {
        this.rating = rating;
        this.review = review;
        this.movie = movie;
        this.reviewer = reviewer;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(final String review) {
        this.review = review;
    }

    public Timestamp getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(final Timestamp reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(final User reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Review review1 = (Review) o;
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
