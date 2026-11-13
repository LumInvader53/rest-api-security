package com.spring.spring_rest.controller;

import com.spring.spring_rest.dto.AuthLoginDto;
import com.spring.spring_rest.dto.AuthRoleRegisterDto;
import com.spring.spring_rest.dto.AuthUserRegisterDto;
import com.spring.spring_rest.entity.Auth_Role;
import com.spring.spring_rest.entity.Auth_User;
import com.spring.spring_rest.repository.AuthRoleRepository;
import com.spring.spring_rest.repository.AuthUserRepository;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes authentication endpoints for role registration,
 * user registration, and login.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;
  private AuthUserRepository authUserRepo;
  private AuthRoleRepository authRoleRepo;
  private BCryptPasswordEncoder passwordEncoder;

  /**
   * Creates an authentication controller with the required services for user
   * authentication and role management.
   *
   * @param authenticationManager the Spring authentication manager used to
   *                              validate login credentials
   * @param authUserRepo          the repository responsible for persisting user
   *                              accounts
   * @param authRoleRepo          the repository responsible for persisting user
   *                              roles
   * @param passwordEncoder       the password encoder used to hash new user
   *                              passwords
   */
  public AuthenticationController(AuthenticationManager authenticationManager,
      AuthUserRepository authUserRepo, AuthRoleRepository authRoleRepo,
      BCryptPasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.authUserRepo = authUserRepo;
    this.authRoleRepo = authRoleRepo;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Registers a new authorized role if it does not already exist.
   *
   * @param authRoleRegisterDto the DTO containing the role name to create
   * @return an HTTP 200 response when the role is created or
   *         an HTTP 400 response if it already exists
   */
  @PostMapping("/register_authorized_role")
  public ResponseEntity<String> register_authRole(
      @RequestBody AuthRoleRegisterDto authRoleRegisterDto) {
    if (authRoleRepo.existsByName(authRoleRegisterDto.getName())) {
      return new ResponseEntity<>(
          authRoleRegisterDto.getName() + " already created.",
          HttpStatus.BAD_REQUEST);
    }

    Auth_Role auth_role = new Auth_Role();
    auth_role.setName(authRoleRegisterDto.getName());

    authRoleRepo.save(auth_role);

    return new ResponseEntity<>(auth_role.getName() +
        " is successfully created", HttpStatus.OK);
  }

  /**
   * Registers a new authorized user with a BCrypt-hashed password
   * and the default USER role.
   *
   * @param authUserRegisterDto the DTO containing the username and password for
   *                            the new user
   * @return an HTTP 200 response when the user is successfully registered or an
   *         HTTP 400 response if the username is already taken
   */
  @PostMapping("/register_authorized_user")
  public ResponseEntity<String> register_authUser(
      @RequestBody AuthUserRegisterDto authUserRegisterDto) {
    if (authUserRepo.existsByUsername(authUserRegisterDto.getUsername())) {
      return new ResponseEntity<>(authUserRegisterDto.getUsername()
          + " is already taken.", HttpStatus.BAD_REQUEST);
    }

    Auth_User auth_user = new Auth_User();
    auth_user.setUsername(authUserRegisterDto.getUsername());
    auth_user.setPassword(
        passwordEncoder.encode(authUserRegisterDto.getPassword()));

    Auth_Role auth_role = authRoleRepo.findByName("USER")
        .orElseThrow(() -> new RuntimeException(
            "Roles USER not found"));
    auth_user.setRoles(Collections.singletonList(auth_role));

    authUserRepo.save(auth_user);

    return new ResponseEntity<>(auth_user.getUsername() +
        " is successfully registered", HttpStatus.OK);
  }

  /**
   * Authenticates the provided user credentials and stores the authenticated
   * principal in the current security context.
   *
   * @param authLoginDto the DTO containing the username and password to
   *                     authenticate
   * @return an HTTP 200 response if the user signs in successfully
   */
  @PostMapping("/auth_login")
  public ResponseEntity<String> login(@RequestBody AuthLoginDto authLoginDto) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authLoginDto.getUsername(), authLoginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);

    return new ResponseEntity<>("Sign in successfully!", HttpStatus.OK);
  }

  public AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }

  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public AuthUserRepository getAuthUserRepo() {
    return authUserRepo;
  }

  public void setAuthUserRepo(AuthUserRepository authUserRepo) {
    this.authUserRepo = authUserRepo;
  }

  public BCryptPasswordEncoder getPasswordEncoder() {
    return passwordEncoder;
  }

  public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public AuthRoleRepository getAuthRoleRepo() {
    return authRoleRepo;
  }

  public void setAuthRoleRepo(AuthRoleRepository authRoleRepo) {
    this.authRoleRepo = authRoleRepo;
  }

}
