package com.revature.repos;

import com.revature.entities.Review;
import com.revature.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    Optional<User> findUserByUsername(String username);
    Set<User> findUsersByUserRole(String role);
    Optional<User> findUserByUsernameAndPassword(String username, String password);

//    @Query(value = "select * from reviews " +
//            "join movie_review on reviewId " +
//            "join user_review on reviewId " +
//            "where userId = :userId " +
//            "and movieId = :movieId")
//    Optional<Review> findExistingReview(int userId, int movieId);
}
