package com.subscription_system.middleware.acceptance.steps;

import com.subscription_system.middleware.dto.Subscription;
import com.subscription_system.middleware.dto.SubscriptionResponse;
import com.subscription_system.middleware.util.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
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

import static com.subscription_system.middleware.acceptance.helpers.RestHelper.builderDeletePost;
import static com.subscription_system.middleware.acceptance.helpers.RestHelper.builderGetPost;
import static org.junit.Assert.assertEquals;

public class SubscriptionStepDefinitions {

  private HttpRequest request;
  private Subscription subscriptionExpected;
  private SubscriptionResponse subscriptionResponseExpected;


  public static final HttpClient httpClient = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_1_1)
    .connectTimeout(Duration.ofSeconds(10))
    .build();
  private CompletableFuture<HttpResponse<String>> response = null;

  public static final String host = (String) Utils.getProp("server", "address", "0.0.0.0");
  public static final Integer port = (Integer) Utils.getProp("server", "port", 9092);

  @Then("the API returns a {int} status code")
  public void returnsHTTPStatusCode(int expectedStatusCode) throws InterruptedException, ExecutionException, TimeoutException {
    response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    int code = response.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);
    assertEquals(expectedStatusCode, code);
  }

  @When("a consumer makes an API GET request {string}")
  public void consumerMakesGet(String uri) throws IOException, InterruptedException {
    request = HttpRequest.newBuilder()
      .GET()
      .uri(URI.create("http://" + host + ":" + port + uri))
      .build();

    //response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  @Given("A subscription that is not stored in our system")
  public void transactionNotStored() {
    this.subscriptionExpected = new Subscription();
    this.subscriptionResponseExpected = new SubscriptionResponse();
  }

  @When("I check the detail from any email and any newsletter_id")
  public void checkDetail() {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) "any@gmail.com");
    params.put("newsletter_id", (String) "1");
    request = builderGetPost("/detail", params);
  }

  @When("I check the detail from any email")
  public void checkAll() {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) "any@gmail.com");
    request = builderGetPost("/allSubscription", params);
  }


  @When("I try to cancel the subscription from any email and any newsletter_id")
  public void cancel() {
    HashMap<String, String> params = new HashMap<>();
    params.put("email", (String) "any@gmail.com");
    params.put("newsletter_id", (String) "1");
    request = builderDeletePost("/cancel", params);
  }

}
