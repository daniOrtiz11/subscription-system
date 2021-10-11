package com.subscription_system.public_service.adapter;

import com.subscription_system.public_service.dto.Subscription;
import com.subscription_system.public_service.dto.SubscriptionResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SubscriptionResponseAdapter {
  public static SubscriptionResponse jsonAdapter(JsonObject jsonObjectReceived) {
    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
    subscriptionResponse.setNewsletterId((String) jsonObjectReceived.getValue("newsletter_id"));
    return subscriptionResponse;
  }

  public static String toJsonAdapter(SubscriptionResponse subscriptionResponse) {
    String header = "{" + "\n";
    String body = "  \"newsletter_id\": " + "\"" + subscriptionResponse.getNewsletterId() + "\"" + "\n";
    String footer = "}";
    return header + body + footer;
  }

  public static List<SubscriptionResponse> jsonArrayAdapter(JsonArray jsonArrayReceived) {
    List objectList = jsonArrayReceived.getList();
    List<SubscriptionResponse> subscriptionList = new ArrayList<>();
    for (Object jsonObject : objectList) {
      LinkedHashMap m = (LinkedHashMap) jsonObject;
      SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
      subscriptionResponse.setNewsletterId((String) m.get("newsletter_id"));
      subscriptionList.add(subscriptionResponse);
    }
    return subscriptionList;
  }
}
