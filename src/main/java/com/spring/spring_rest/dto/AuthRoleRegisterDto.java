package com.spring.spring_rest.dto;

/**
 * Data transfer object used to register an authentication role.
 */
public class AuthRoleRegisterDto {

  private String name;

  public AuthRoleRegisterDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
