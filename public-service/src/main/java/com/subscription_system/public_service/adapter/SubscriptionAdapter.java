package com.subscription_system.public_service.adapter;

import com.subscription_system.public_service.dto.Subscription;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionAdapter {
  public static Subscription jsonAdapter(JsonObject jsonObjectReceived) {
    Subscription subscription = new Subscription();
    subscription.setBirth_date((String) jsonObjectReceived.getValue("birth_date"));
    subscription.setConsent((Boolean) jsonObjectReceived.getValue("consent"));
    subscription.setEmail((String) jsonObjectReceived.getValue("email"));
    subscription.setGender((String) jsonObjectReceived.getValue("gender"));
    subscription.setNewsletterId((String) jsonObjectReceived.getValue("newsletter_id"));
    return subscription;
  }

  public static List<Subscription> jsonArrayAdapter(JsonArray jsonArrayReceived) {
    List<JsonObject> ObjectList = jsonArrayReceived.getList();
    List<Subscription> subscriptionList = new ArrayList<>();
    for (JsonObject jsonObject : ObjectList) {
      subscriptionList.add(jsonAdapter(jsonObject));
    }
    return subscriptionList;
  }
}
