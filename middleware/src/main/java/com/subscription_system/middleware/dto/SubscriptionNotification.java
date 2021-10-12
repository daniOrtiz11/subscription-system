package com.subscription_system.middleware.dto;

public class SubscriptionNotification {

  private String newsletter_id;
  private String email;
  private boolean subscription;

  public SubscriptionNotification(String newsletter_id, String email, boolean subscription) {
    this.newsletter_id = newsletter_id;
    this.email = email;
    this.subscription = subscription;
  }

  public SubscriptionNotification() {
  }

  public String getNewsletter_id() {
    return newsletter_id;
  }

  public void setNewsletter_id(String newsletter_id) {
    this.newsletter_id = newsletter_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isSubscription() {
    return subscription;
  }

  public void setSubscription(boolean subscription) {
    this.subscription = subscription;
  }
}
