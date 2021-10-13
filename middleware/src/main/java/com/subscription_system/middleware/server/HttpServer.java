package com.subscription_system.middleware.server;

import com.subscription_system.middleware.handler.HealthHandler;
import com.subscription_system.middleware.handler.SubscriptionGetHandler;
import com.subscription_system.middleware.handler.SubscriptionHandler;
import com.subscription_system.middleware.util.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;

public class HttpServer extends AbstractVerticle {

  static final String DEFAULT_HOST = (String) Utils.getProp("server", "address", "0.0.0.0");
  static final Integer DEFAULT_PORT = (Integer) Utils.getProp("server", "port", 9092);
  static final String SWAGGER_URL = (String) Utils.getProp("server","swagger_url", "src/main/resources/swagger.yml");

  private HealthHandler healthHandler;
  private SubscriptionHandler subscriptionHandler;
  private SubscriptionGetHandler subscriptionGetHandler;

  public void start(Promise<Void> startPromise) {
    this.healthHandler = new HealthHandler();
    this.subscriptionHandler = new SubscriptionHandler(vertx);
    this.subscriptionGetHandler = new SubscriptionGetHandler(vertx);

    RouterBuilder.create(this.vertx, SWAGGER_URL)
      .onSuccess(routerBuilder -> {
        routerBuilder.operation("getHealthCheckRequest").handler(routingContext ->
          healthHandler.healthCheck(routingContext));
        routerBuilder.operation("postCreateSubscription").handler(routingContext ->
          subscriptionHandler.create(routingContext));
        routerBuilder.operation("deleteCancelSubscription").handler(routingContext ->
          subscriptionHandler.cancel(routingContext));
        routerBuilder.operation("getDetailSubscription").handler(routingContext ->
          subscriptionGetHandler.getDetail(routingContext));
        routerBuilder.operation("getAllSubscription").handler(routingContext ->
          subscriptionGetHandler.getAll(routingContext));
        Router router = routerBuilder.createRouter();
        var server = vertx.createHttpServer(new HttpServerOptions().setPort(DEFAULT_PORT).setHost(DEFAULT_HOST));
        server.requestHandler(router).listen();
      }).onFailure(startPromise::fail);
  }
}
