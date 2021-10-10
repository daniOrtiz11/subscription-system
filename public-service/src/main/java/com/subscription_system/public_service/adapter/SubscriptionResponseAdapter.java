package com.subscription_system.public_service.adapter;

import com.subscription_system.public_service.dto.SubscriptionResponse;
import io.vertx.core.json.JsonObject;

public class SubscriptionResponseAdapter {
  public static SubscriptionResponse jsonAdapter(JsonObject jsonObjectReceived) {
    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    subscriptionResponse.setNewsletterId((String) jsonObjectReceived.getValue("newsletterId"));
    return subscriptionResponse;
  }

  public static String toJsonAdapter(SubscriptionResponse subscriptionResponse) {
    String header = "{" + "\n";
    String body = "  \"newsletterId\": " + "\"" + subscriptionResponse.getNewsletterId() + "\"" + "\n";
    String footer = "}";
    return header + body + footer;
  }
}
