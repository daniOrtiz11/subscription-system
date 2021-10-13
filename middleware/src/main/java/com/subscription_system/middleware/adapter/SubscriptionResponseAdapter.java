package com.subscription_system.middleware.adapter;

import com.subscription_system.middleware.dto.SubscriptionResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class SubscriptionResponseAdapter {
  public static SubscriptionResponse jsonAdapter(JsonObject jsonObjectReceived) {
    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    subscriptionResponse.setNewsletter_id((String) jsonObjectReceived.getValue("newsletter_id"));
    return subscriptionResponse;
  }

  public static String toJsonAdapter(SubscriptionResponse subscriptionResponse) {
    String header = "{" + "\n";
    String body = "  \"newsletter_id\": " + "\"" + subscriptionResponse.getNewsletter_id() + "\"" + "\n";
    String footer = "}";
    return header + body + footer;
  }
  public static JsonArray toJsonArrayAdapter (List<SubscriptionResponse> subscriptionsResponse) {
    return new JsonArray(subscriptionsResponse);
  }
}
