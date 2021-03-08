package com.revature.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public Review(){
        super();
    }

    public Review(double rating, String review) {
        this.rating = rating;
        this.review = review;
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
