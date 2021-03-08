package com.revature.repos;

import com.revature.entities.Movie;
import com.revature.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {


    Optional<Movie> findMovieByName(String name);
    List<Movie> findMoviesByYear(int year);
    List<Movie> findMoviesByGenre(String genre);
    List<Movie> findMoviesByDirector(String director);

}
