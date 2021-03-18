package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *Class which represent a User of the site.
 */
@Entity @Table(name = "users")
public class User {

    @Id @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column
    private String bio;

    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> userFavorites;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_reviews",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> userReviews;

    public User(){
        super();
    }

    public User(final String username,final String password,final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        userFavorites = new ArrayList<>();
        userReviews = new ArrayList<>();
    }

    public User(String username, String password, String email, String firstName, String lastName, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Movie> getUserFavorites() {
        return userFavorites;
    }

    public void addMovieToFavorites(final Movie movie) {
        if(userFavorites == null) {
            userFavorites = new ArrayList<>();
        }
        userFavorites.add(movie);
    }

    public void setUserFavorites(final List<Movie> userFavorites) {
        this.userFavorites = userFavorites;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(final List<Review> userReviews) {
        this.userReviews = userReviews;
    }

    public void removeMovieFromFavorites(final Movie movie) {
        userFavorites.remove(movie);
    }

    public void addReview(final Review review){
        if (userReviews == null) {
            userReviews = new ArrayList<>();
        }
        userReviews.add(review);
    }

    public void removeReview(final Review review) { userReviews.remove(review); }


@Override
public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final User user = (User) o;
    return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(bio, user.bio) && userRole == user.userRole;
}

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, firstName, lastName, bio, userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}