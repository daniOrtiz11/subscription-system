package com.subscription_system.public_service.service;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface Service {
  JsonObject create(JsonObject body) throws InterruptedException, ExecutionException, TimeoutException;

  JsonObject cancel(Object email, Object subscriptionId) throws InterruptedException, ExecutionException, TimeoutException;

  JsonObject getDetail(Object email, Object subscriptionId) throws InterruptedException, ExecutionException, TimeoutException;

  JsonArray getAll(Object email) throws InterruptedException, ExecutionException, TimeoutException;
}
