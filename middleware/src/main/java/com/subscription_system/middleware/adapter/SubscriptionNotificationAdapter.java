package com.subscription_system.middleware.adapter;

import com.subscription_system.middleware.dto.SubscriptionNotification;
import com.subscription_system.middleware.dto.SubscriptionResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class SubscriptionNotificationAdapter {

  public static JsonArray toJsonArrayAdapter (List<SubscriptionNotification> subscriptionsNotification) {
    return new JsonArray(subscriptionsNotification);
  }
}
