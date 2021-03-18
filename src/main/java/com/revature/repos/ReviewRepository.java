package com.revature.repos;

import com.revature.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository which handles sending and receiving Reviews from and to the client.
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    Optional<Review> findReviewById(final int id);


}
