package com.spring.spring_rest.service;

import com.spring.spring_rest.entity.Auth_Role;
import com.spring.spring_rest.entity.Auth_User;
import com.spring.spring_rest.repository.AuthUserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads application users from the repository and
 * translates their assigned roles into Spring Security
 * authorities for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final AuthUserRepository authUserRepo;

  /**
   * Creates a user-details service backed by the
   * authentication user repository.
   *
   * @param authUserRepo the repository used to look up users by username
   */
  public CustomUserDetailsService(AuthUserRepository authUserRepo) {
    this.authUserRepo = authUserRepo;
  }

  /**
   * Loads a user by username and converts the user's roles
   * into Spring Security authorities.
   *
   * @param theUsername the username to look up
   * @return the authenticated user details used by Spring Security
   * @throws UsernameNotFoundException if the username does not exist
   */
  @Override
  public UserDetails loadUserByUsername(String theUsername) {
    Auth_User user = authUserRepo.findByUsername(theUsername)
        .orElseThrow(
            () -> new UsernameNotFoundException(
                "Username " + theUsername + " does not exists"));
    return new User(
        user.getUsername(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles()));
  }

  /**
   * Maps application roles to Spring Security granted authorities.
   *
   * @param roles the roles assigned to the user
   * @return the corresponding granted authorities
   */
  private Collection<GrantedAuthority> mapRolesToAuthorities(
      List<Auth_Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
