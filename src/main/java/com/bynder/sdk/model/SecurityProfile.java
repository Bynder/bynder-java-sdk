package com.bynder.sdk.model;

import java.util.List;

public class SecurityProfile {

  private String id;
  private String name;

  private List<String> roles;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getRoles() {
    return roles;
  }
}
