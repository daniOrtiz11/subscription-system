package com.subscription_system.middleware.service;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface Service {
  void create(Object body, String channel);
  void cancel(String email, String subscriptionId, String channel);
  void getDetail(String email, String subscriptionId, String channel);
  void getAll(String email, String channel);
  void notifyEmailService(String email, String newsletter_id, boolean created);
}
