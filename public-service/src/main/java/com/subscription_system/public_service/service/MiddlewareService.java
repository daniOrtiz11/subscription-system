package com.subscription_system.public_service.service;

import com.subscription_system.public_service.adapter.SubscriptionAdapter;
import com.subscription_system.public_service.adapter.SubscriptionResponseAdapter;
import com.subscription_system.public_service.util.Utils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MiddlewareService implements Service {

  public static final String MIDDLEWARE_HOST = (String) Utils.getProp("middleware", "address", "0.0.0.0");
  public static final Integer MIDDLEWARE_PORT = (Integer) Utils.getProp("middleware", "port", 9091);

  private final HttpClient httpClient = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_1_1)
    .connectTimeout(Duration.ofSeconds(10))
    .build();

  @Override
  public JsonObject create(JsonObject body) throws InterruptedException, ExecutionException, TimeoutException {
    HttpRequest request = this.builderJsonPost("/subscription", body.encode());
    CompletableFuture<HttpResponse<String>> responseCreate =
      httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    String bodyResponse = responseCreate.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    int code = responseCreate.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
    if (bodyResponse != null && code == 200) {
      try {
        JsonObject jsonObjectReceived = Utils.readJsonByString(bodyResponse);
        SubscriptionResponseAdapter.jsonAdapter(jsonObjectReceived);
        return jsonObjectReceived;
      } catch (Exception e) {
        return null;
      }
    } else {
      return null;
    }
  }

  @Override
  public JsonObject cancel(Object email, Object subscriptionId) throws InterruptedException, ExecutionException, TimeoutException {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) email);
    params.put("newsletter_id", (String) subscriptionId);
    HttpRequest request = this.builderDelete("/cancel", params);
    CompletableFuture<HttpResponse<String>> responseCreate =
      httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String bodyResponse = responseCreate.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    int code = responseCreate.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
    if (bodyResponse != null && code == 200) {
      try {
        JsonObject jsonObjectReceived = Utils.readJsonByString(bodyResponse);
        SubscriptionResponseAdapter.jsonAdapter(jsonObjectReceived);
        return jsonObjectReceived;
      } catch (Exception e) {
        return null;
      }
    } else {
      return null;
    }
  }

  @Override
  public JsonObject getDetail(Object email, Object subscriptionId) throws InterruptedException, ExecutionException, TimeoutException {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) email);
    params.put("newsletter_id", (String) subscriptionId);
    HttpRequest request = this.builderGet("/detail", params);
    CompletableFuture<HttpResponse<String>> responseCreate =
      httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String bodyResponse = responseCreate.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    int code = responseCreate.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
    if (code == 200 && bodyResponse != null) {
      try {
        JsonArray jsonArrayReceived = Utils.readJsonArrayByString(bodyResponse);
        JsonObject jsonObjectReceived = jsonArrayReceived.getJsonObject(0);
        SubscriptionAdapter.jsonAdapter(jsonObjectReceived);
        return jsonObjectReceived;
      } catch (Exception e) {
        return null;
      }
    } else {
      return null;
    }
  }

  @Override
  public JsonArray getAll(Object email) throws InterruptedException, ExecutionException, TimeoutException {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) email);
    HttpRequest request = this.builderGet("/all", params);
    CompletableFuture<HttpResponse<String>> responseCreate =
      httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    String bodyResponse = responseCreate.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    int code = responseCreate.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
    if (code == 200 && bodyResponse != null) {
      try {
        JsonArray jsonArrayReceived = Utils.readJsonArrayByString(bodyResponse);
        SubscriptionResponseAdapter.jsonArrayAdapter(jsonArrayReceived);
        return jsonArrayReceived;
      } catch (Exception e) {
        return null;
      }
    } else {
      return null;
    }
  }


  private HttpRequest builderJsonPost(String endpoint, String body) {
    return HttpRequest.newBuilder()
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .uri(URI.create("http://" + MIDDLEWARE_HOST + ":" + MIDDLEWARE_PORT + endpoint))
      .header("Content-Type", "application/json")
      .build();
  }


  private HttpRequest builderDelete(String endpoint, HashMap<String, String> params) {
    HttpRequest.Builder base =  HttpRequest.newBuilder()
      .DELETE()
      .uri(URI.create("http://" + MIDDLEWARE_HOST + ":" + MIDDLEWARE_PORT + endpoint));

    for (HashMap.Entry entry : params.entrySet()) {
      base.header((String)entry.getKey(), (String)entry.getValue());
    }
    return base.build();
  }

  private HttpRequest builderGet(String endpoint, HashMap<String, String> params) {
    HttpRequest.Builder base =  HttpRequest.newBuilder()
      .GET()
      .uri(URI.create("http://" + MIDDLEWARE_HOST + ":" + MIDDLEWARE_PORT + endpoint));

    for (HashMap.Entry entry : params.entrySet()) {
      base.header((String)entry.getKey(), (String)entry.getValue());
    }
    return base.build();
  }

}
