package com.subscription_system.middleware.adapter;

import com.subscription_system.middleware.dto.Subscription;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;

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
    subscription.setName((String) jsonObjectReceived.getValue("name"));
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

  public static Subscription sqlRowAdapter(Row row) {
    Subscription subscription = new Subscription();
    subscription.setBirth_date(row.getString("birth_date"));
    subscription.setConsent(row.getBoolean("consent"));
    subscription.setEmail(row.getString("email"));
    subscription.setGender(row.getString("gender"));
    subscription.setNewsletterId(row.getString("newsletter_id"));
    subscription.setName(row.getString("name"));
    return subscription;
  }

  public static JsonArray toJsonArrayAdapter (List<Subscription> subscriptions) {
    return new JsonArray(subscriptions);
  }
}
