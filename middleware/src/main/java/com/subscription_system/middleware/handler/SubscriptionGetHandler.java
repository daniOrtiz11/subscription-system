package com.subscription_system.middleware.handler;

import com.subscription_system.middleware.service.MiddlewareService;
import com.subscription_system.middleware.service.Service;
import com.subscription_system.middleware.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class SubscriptionGetHandler {

  private final Service middlewareService;
  private final EventBus eventBus;
  private final String CH_SUFFIX = "SubscriptionSearchHandler";

  public SubscriptionGetHandler(Vertx vertx) {

    this.middlewareService = new MiddlewareService();
    this.eventBus = vertx.eventBus();

  }

  public void getDetail(RoutingContext routingContext) {
    String email = routingContext.request().getHeader("email");
    String newsletterId = routingContext.request().getHeader("newsletter_id");
    String channel = Utils.generateChannel("getDetail", CH_SUFFIX);
    try {
      eventBus.consumer(channel, received -> {
        if (received.body() != null) {
          routingContext.response()
            .putHeader("Content-type", "application/json")
            .setStatusCode(OK.code())
            .end((String) received.body());
        } else {
          routingContext.response()
            .setStatusCode(METHOD_NOT_ALLOWED.code()).end();
        }
      });
      this.middlewareService.getDetail(email, newsletterId, channel);
    } catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }

  public void getAll(RoutingContext routingContext) {
    String email = routingContext.request().getHeader("email");
    String channel = Utils.generateChannel("getAll", CH_SUFFIX);
    try {
      eventBus.consumer(channel, received -> {
        if (received.body() != null) {
          routingContext.response()
            .putHeader("Content-type", "application/json")
            .setStatusCode(OK.code())
            .end((String) received.body());
        } else {
          routingContext.response()
            .setStatusCode(METHOD_NOT_ALLOWED.code()).end();
        }
      });
      this.middlewareService.getAll(email, channel);
    } catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }
}
