package com.subscription_system.public_service.handler;

import io.vertx.ext.web.RoutingContext;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class HealthHandler {

  public void healthCheck(final RoutingContext context) {
    context.response()
      .setStatusCode(OK.code())
      .end();
  }
}
