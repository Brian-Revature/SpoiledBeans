package com.revature.repos;

import com.revature.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to handle sending and retrieving movie data between client and database.
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {


    Optional<Movie> findMovieByName(final String name);
    List<Movie> findMoviesByYear(final int year);
    List<Movie> findMoviesByGenre(final String genre);
    List<Movie> findMoviesByDirector(final String director);

    //Get the average rating for a given movie by movie name
    @Query(value = "SELECT AVG(r.rating) FROM review r JOIN movie_review mr ON mr.review_id = r.id JOIN movies m ON m.id = mr.movie_id WHERE m.name = ?1", nativeQuery = true)
    double findMovieRating(final String name);

}
