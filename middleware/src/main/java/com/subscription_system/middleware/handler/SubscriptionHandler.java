package com.subscription_system.middleware.handler;

import com.subscription_system.middleware.service.MiddlewareService;
import com.subscription_system.middleware.service.Service;
import com.subscription_system.middleware.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class SubscriptionHandler {

  private final Service middlewareService;
  private final EventBus eventBus;
  private final String CH_SUFFIX = "SubscriptionHandler";
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


  public SubscriptionHandler(Vertx vertx) {

    this.middlewareService = new MiddlewareService();
    this.eventBus = vertx.eventBus();

  }

  public void create(RoutingContext routingContext) {
    String channel = Utils.generateChannel("create", CH_SUFFIX);
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
      this.middlewareService.create(routingContext.getBodyAsJson(), channel);
    } catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }

  public void cancel(RoutingContext routingContext) {
    String email = routingContext.request().getHeader("email");
    String newsletterId = routingContext.request().getHeader("newsletter_id");
    String channel = Utils.generateChannel("cancel", CH_SUFFIX);
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
      this.middlewareService.cancel(email, newsletterId, channel);
    } catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }
}
