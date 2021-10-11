package com.subscription_system.middleware.service;

import com.subscription_system.middleware.adapter.SubscriptionResponseAdapter;
import com.subscription_system.middleware.dto.Subscription;
import com.subscription_system.middleware.dto.SubscriptionResponse;
import com.subscription_system.middleware.storage.PostgreSQLStorage;
import com.subscription_system.middleware.storage.Storage;
import com.subscription_system.middleware.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import static com.subscription_system.middleware.adapter.SubscriptionAdapter.jsonAdapter;

public class MiddlewareService implements Service {

  private final String CH_SUFFIX = "SubscriptionService";

  private final EventBus eventBus;
  private final EventBus eventHandlerBus;
  private final Storage storage;

  public MiddlewareService(){
    eventBus = Vertx.currentContext().owner().eventBus();
    eventHandlerBus = Vertx.currentContext().owner().eventBus();
    this.storage = new PostgreSQLStorage();
  }

  @Override
  public void create(Object body, String channel) {
    Subscription subscription = jsonAdapter((JsonObject) body);
    String channelService =  Utils.generateChannel("create", CH_SUFFIX);
    this.storage.insertSubscription(subscription, channelService);
    eventBus.consumer(channelService, resultCreate -> {
      if (resultCreate != null) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse((String) resultCreate.body());
        eventHandlerBus.request(channel, SubscriptionResponseAdapter.toJsonAdapter(subscriptionResponse));
      } else {
        eventHandlerBus.request(channel, null);
      }
    });
  }

  @Override
  public void cancel(String email, String subscriptionId, String channel) {
    String channelService =  Utils.generateChannel("cancel", CH_SUFFIX);
    this.storage.deleteSubscription(email, subscriptionId, channelService);
    eventBus.consumer(channelService, resultCreate -> {
      if (resultCreate != null && resultCreate.body() != null) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse((String) resultCreate.body());
        eventHandlerBus.request(channel, SubscriptionResponseAdapter.toJsonAdapter(subscriptionResponse));
      } else {
        eventHandlerBus.request(channel, null);
      }
    });
  }

  @Override
  public void getDetail(String email, String subscriptionId, String channel) {
    String channelService =  Utils.generateChannel("getDetail", CH_SUFFIX);
    this.storage.getSubscription(email, subscriptionId, channelService);
    eventBus.consumer(channelService, resultCreate -> {
      if (resultCreate != null && resultCreate.body() != null) {
        eventHandlerBus.request(channel, resultCreate.body());
      } else {
        eventHandlerBus.request(channel, null);
      }
    });
  }

  @Override
  public void getAll(String email, String channel) {
    String channelService =  Utils.generateChannel("getAll", CH_SUFFIX);
    this.storage.getSubscriptionByEmail(email, channelService);
    eventBus.consumer(channelService, resultCreate -> {
      if (resultCreate != null) {
        eventHandlerBus.request(channel, resultCreate.body());
      } else {
        eventHandlerBus.request(channel, null);
      }
    });
  }

}
