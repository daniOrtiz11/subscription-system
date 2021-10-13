package com.subscription_system.middleware.dto;

public class Subscription {

  private String email;
  private String name;
  private String gender;
  private String birth_date;
  private boolean consent;
  private String newsletterId;

  public Subscription(String email, String name, String gender, String birth_date, boolean consent, String newsletterId) {
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.birth_date = birth_date;
    this.consent = consent;
    this.newsletterId = newsletterId;
  }

  public Subscription() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirth_date() {
    return birth_date;
  }

  public void setBirth_date(String birth_date) {
    this.birth_date = birth_date;
  }

  public boolean isConsent() {
    return consent;
  }

  public void setConsent(boolean consent) {
    this.consent = consent;
  }

  public String getNewsletterId() {
    return newsletterId;
  }

  public void setNewsletterId(String newsletterId) {
    this.newsletterId = newsletterId;
  }
}
