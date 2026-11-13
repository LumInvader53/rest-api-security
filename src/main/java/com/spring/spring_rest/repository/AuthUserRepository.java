package com.spring.spring_rest.repository;

import com.spring.spring_rest.entity.Auth_User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing authentication user entities.
 */
public interface AuthUserRepository extends JpaRepository<Auth_User, Long> {

  /**
   * Finds a user by the provided username.
   *
   * @param theUsername the username to search for
   * @return an optional containing the matching user, if found
   */
  Optional<Auth_User> findByUsername(String theUsername);

  /**
   * Checks whether a user with the provided username exists.
   *
   * @param theUsername the username to check
   * @return true if a matching user exists, otherwise false
   */
  Boolean existsByUsername(String theUsername);
}
