package com.subscription_system.public_service.server;

import com.subscription_system.public_service.handler.HealthHandler;
import com.subscription_system.public_service.handler.SubscriptionHandler;
import com.subscription_system.public_service.util.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;

public class HttpServer extends AbstractVerticle {

  static final String DEFAULT_HOST = (String) Utils.getProp("server", "address", "0.0.0.0");
  static final Integer DEFAULT_PORT = (Integer) Utils.getProp("server", "port", 9091);
  static final String SWAGGER_URL = (String) Utils.getProp("server","swagger_url", "src/main/resources/swagger.yml");

  private HealthHandler healthHandler;
  private SubscriptionHandler subscriptionHandlerHandler;

  public void start(Promise<Void> startPromise) {
    this.healthHandler = new HealthHandler();
    this.subscriptionHandlerHandler = new SubscriptionHandler();

    RouterBuilder.create(this.vertx, SWAGGER_URL)
      .onSuccess(routerBuilder -> {
        routerBuilder.operation("getHealthCheckRequest").handler(routingContext ->
          healthHandler.healthCheck(routingContext));
        routerBuilder.operation("postCreateSubscription").handler(routingContext ->
          subscriptionHandlerHandler.create(routingContext));
        routerBuilder.operation("deleteCancelSubscription").handler(routingContext ->
          subscriptionHandlerHandler.cancel(routingContext));
        routerBuilder.operation("getDetailSubscription").handler(routingContext ->
          subscriptionHandlerHandler.getDetail(routingContext));
        routerBuilder.operation("getAllSubscription").handler(routingContext ->
          subscriptionHandlerHandler.getAll(routingContext));

        Router router = routerBuilder.createRouter();
        var server = vertx.createHttpServer(new HttpServerOptions().setPort(DEFAULT_PORT).setHost(DEFAULT_HOST));
        server.requestHandler(router).listen();
      }).onFailure(startPromise::fail);
  }
}
