package com.subscription_system.public_service.dto;

import com.subscription_system.public_service.handler.SubscriptionHandler;

public class SubscriptionResponse {
  private String newsletterId;

  public SubscriptionResponse(){

  }

  public SubscriptionResponse(String newsletterId) {
    this.newsletterId = newsletterId;
  }

  public String getNewsletterId() {
    return newsletterId;
  }

  public void setNewsletterId(String newsletterId) {
    this.newsletterId = newsletterId;
  }
}
