package com.revature.dtos;

import com.revature.entities.Movie;

import java.util.List;
import java.util.Objects;

/**
 * A DTO to facilitate sending User Favorite Movies between databse and client.
 */
public class FavoritesDTO {
    private String username;
    private List<Movie> favorites;

    public FavoritesDTO() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<Movie> getFavorites() {
        return favorites;
    }

    public void setFavorites(final List<Movie> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FavoritesDTO that = (FavoritesDTO) o;
        return username.equals(that.username) && Objects.equals(favorites, that.favorites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, favorites);
    }

    @Override
    public String toString() {
        return "FavoritesDTO{" +
                "username='" + username + '\'' +
                ", favorites=" + favorites +
                '}';
    }

}
