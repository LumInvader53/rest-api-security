package com.spring.spring_rest.repository;

import com.spring.spring_rest.entity.Auth_Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing authentication roles.
 */
public interface AuthRoleRepository extends JpaRepository<Auth_Role, Long> {

  /**
   * Finds an authentication role by its name.
   *
   * @param theName the role name to search for
   * @return the matching role, if present
   */
  Optional<Auth_Role> findByName(String theName);

  /**
   * Checks whether an authentication role with the given name exists.
   *
   * @param theUsername the role name to check
   * @return true if a role with the provided name exists, otherwise false
   */
  Boolean existsByName(String theUsername);
}
