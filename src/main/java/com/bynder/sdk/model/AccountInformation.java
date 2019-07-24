package com.bynder.sdk.model;

import java.util.List;

public class AccountInformation {

  private String defaultLanguage;
  private String name;
  private String timeZone;

  private List<String> availableLanguages;

  public String getDefaultLanguage() {
    return defaultLanguage;
  }

  public String getName() {
    return name;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public List<String> getAvailableLanguages() {
    return availableLanguages;
  }
}
