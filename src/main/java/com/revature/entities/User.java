package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        userFavorites = new ArrayList<>();
        userReviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
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

    public void setUserFavorites(List<Movie> userFavorites) {
        this.userFavorites = userFavorites;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
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

    public void removeMovieFromFavorites(final Movie movie) {
        userFavorites.remove(movie);
    }
}
