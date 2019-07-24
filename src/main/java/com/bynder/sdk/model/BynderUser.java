package com.bynder.sdk.model;

import java.util.List;

public class BynderUser {

  private String id;
  private String email;
  private String email2;
  private String name;
  private Boolean active;
  private String username;

  private List<Group> groups;

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getName() {
    return name;
  }

  public Boolean getActive() {
    return active;
  }

  public String getUsername() {
    return username;
  }

  public List<Group> getGroups() {
    return groups;
  }

}
