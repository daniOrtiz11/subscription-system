package com.subscription_system.public_service.handler;

import com.subscription_system.public_service.service.MiddlewareService;
import com.subscription_system.public_service.service.Service;
import com.subscription_system.public_service.util.Utils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class SubscriptionHandler {

  private final Service subscriptionService;

  public SubscriptionHandler() {
    this.subscriptionService = new MiddlewareService();
  }

  public void create(RoutingContext routingContext) {
    try {
      String access_token = routingContext.queryParam("access_token").get(0);
      JsonObject body = routingContext.getBodyAsJson();
      if (Utils.checkToken(access_token) && Utils.checkMail(body.getString("email"))) {
         JsonObject jsonResponse = this.subscriptionService.create(body);
         if (jsonResponse != null) {
           routingContext.response()
             .setStatusCode(OK.code())
             .putHeader("Content-type", "application/json")
             .end(jsonResponse.encode());
         }
       }
    } catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }

  public void cancel(RoutingContext routingContext) {
    try {
      String access_token = routingContext.queryParam("access_token").get(0);
      Object subscriptionId = routingContext.pathParam("subscription_id");
      Object email = routingContext.queryParam("email");
      if (Utils.checkToken(access_token) && Utils.checkMail((String) email)) {
        JsonObject jsonResponse = this.subscriptionService.cancel(email, subscriptionId);
        if (jsonResponse != null) {
          routingContext.response()
            .setStatusCode(OK.code())
            .putHeader("Content-type", "application/json")
            .end(jsonResponse.toString());
        }
      } else {
         routingContext.response()
          .setStatusCode(METHOD_NOT_ALLOWED.code()).end();
      }
    }  catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }

  public void getDetail(RoutingContext routingContext) {
    try {
      String access_token = routingContext.queryParam("access_token").get(0);
      Object subscriptionId = routingContext.pathParam("subscription_id");
      Object email = routingContext.queryParam("email");
      if (Utils.checkToken(access_token) && Utils.checkMail((String) email)) {
        JsonObject jsonResponse = this.subscriptionService.getDetail(email, subscriptionId);
        if (jsonResponse != null) {
          routingContext.response()
            .setStatusCode(OK.code())
            .putHeader("Content-type", "application/json")
            .end(jsonResponse.toString());
        }
      } else {
        routingContext.response()
          .setStatusCode(METHOD_NOT_ALLOWED.code()).end();
      }
    }  catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }

  public void getAll(RoutingContext routingContext) {
    try {
      Object email = routingContext.queryParam("email");
      String access_token = routingContext.queryParam("access_token").get(0);
      if (Utils.checkToken(access_token) && Utils.checkMail((String) email)) {
        JsonObject jsonResponse = this.subscriptionService.getAll(email);
        if (jsonResponse != null) {
          routingContext.response()
            .setStatusCode(OK.code())
            .putHeader("Content-type", "application/json")
            .end(jsonResponse.toString());
        }
      } else {
        routingContext.response()
          .setStatusCode(METHOD_NOT_ALLOWED.code()).end();
      }
    }  catch (Exception e) {
      routingContext.response()
        .setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
    }
  }
}
