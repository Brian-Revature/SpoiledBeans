package com.revature.repos;

import com.revature.entities.Review;
import com.revature.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    Optional<Review> findReviewById(int id);


}
