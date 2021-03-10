package com.revature.dtos;

import com.revature.entities.Movie;

import java.util.List;
import java.util.Objects;

public class FavoritesDTO {
    public FavoritesDTO() {
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Movie> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Movie> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritesDTO that = (FavoritesDTO) o;
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

    private List<Movie> favorites;
}
