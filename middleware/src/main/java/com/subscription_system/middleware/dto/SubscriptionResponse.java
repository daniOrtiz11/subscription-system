package com.subscription_system.middleware.dto;

public class SubscriptionResponse {
  private String newsletter_id;

  public SubscriptionResponse(){

  }

  public SubscriptionResponse(String newsletterId) {
    this.newsletter_id = newsletterId;
  }

  public String getNewsletter_id() {
    return newsletter_id;
  }

  public void setNewsletter_id(String newsletter_id) {
    this.newsletter_id = newsletter_id;
  }
}
