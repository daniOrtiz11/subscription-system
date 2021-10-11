package com.subscription_system.middleware.acceptance.helpers;

import com.subscription_system.middleware.acceptance.steps.SubscriptionStepDefinitions;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;

public class RestHelper {
  public static HttpRequest builderJsonPost(String endpoint, String body) {
    return HttpRequest.newBuilder()
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .uri(URI.create("http://" + SubscriptionStepDefinitions.host + ":" + SubscriptionStepDefinitions.port + endpoint))
      .header("Content-Type", "application/json")
      .build();
  }

  public static HttpRequest builderGetPost(String endpoint, HashMap<String, String> params) {
    HttpRequest.Builder base =  HttpRequest.newBuilder()
      .GET()
      .uri(URI.create("http://" + SubscriptionStepDefinitions.host + ":" + SubscriptionStepDefinitions.port  + endpoint));

    for (HashMap.Entry entry : params.entrySet()) {
      base.header((String)entry.getKey(), (String)entry.getValue());
    }
    return base.build();
  }

  public static HttpRequest builderDeletePost(String endpoint, HashMap<String, String> params) {
    HttpRequest.Builder base =  HttpRequest.newBuilder()
      .DELETE()
      .uri(URI.create("http://" + SubscriptionStepDefinitions.host + ":" + SubscriptionStepDefinitions.port  + endpoint));

    for (HashMap.Entry entry : params.entrySet()) {
      base.header((String)entry.getKey(), (String)entry.getValue());
    }
    return base.build();
  }

}
