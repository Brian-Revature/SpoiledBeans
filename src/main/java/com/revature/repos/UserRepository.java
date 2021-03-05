package com.revature.repos;

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

}
