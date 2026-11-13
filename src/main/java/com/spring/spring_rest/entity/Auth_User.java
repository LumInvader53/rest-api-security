package com.spring.spring_rest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an application user with
 * authentication credentials and assigned roles.
 */
@Entity
@Table(name = "auth_user")
public class Auth_User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  /**
   * The set of roles granted to this user.
   * The roles are loaded eagerly and cascade all persistence operations.
   */
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "authusers_authroles",
      joinColumns = @JoinColumn(name = "authuser_id",
        referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "authrole_id",
        referencedColumnName = "id"))
  private List<Auth_Role> roles = new ArrayList<>();

  /**
   * Default constructor required by JPA.
   */
  public Auth_User() {
  }

  /**
   * Creates a user with the supplied username, password, and role assignments.
   *
   * @param username the unique username for authentication
   * @param password the encoded password for the user
   * @param roles the roles assigned to the user
   */
  public Auth_User(String username, String password, List<Auth_Role> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  /**
   * Returns the database identifier for this user.
   *
   * @return the user ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the database identifier for this user.
   *
   * @param id the user ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the username used to authenticate the user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username used to authenticate the user.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the stored password for this user.
   *
   * @return the password value
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the stored password for this user.
   *
   * @param password the password value
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the list of roles assigned to this user.
   *
   * @return the user roles
   */
  public List<Auth_Role> getRoles() {
    return roles;
  }

  /**
   * Sets the list of roles assigned to this user.
   *
   * @param roles the user roles
   */
  public void setRoles(List<Auth_Role> roles) {
    this.roles = roles;
  }

}
