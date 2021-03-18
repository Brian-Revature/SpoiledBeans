package com.revature.repos;

import com.revature.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.Set;

/**
 * Repository for handling sending and retrieving User data
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findUserByUsername(final String username);
    Set<User> findUsersByUserRole(final String role);
    Optional<User> findUserByUsernameAndPassword(final String username,final String password);
}
