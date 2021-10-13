package com.subscription_system.email_notifier;

import com.subscription_system.email_notifier.kafka.KafkaConsumer;
import com.subscription_system.email_notifier.util.Utils;
import io.netty.channel.DefaultChannelId;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) {
    LOGGER.info("Hello! Starting email_notifier app!");
    DefaultChannelId.newInstance();
    DeploymentOptions defaultDeplOpt = new DeploymentOptions();
    defaultDeplOpt.setInstances(1);
    VertxOptions vertxOptions = new VertxOptions();
    final Vertx vertx = Vertx.vertx(vertxOptions);
    String configPath = "config.yaml";
    ConfigStoreOptions store;
    store = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject()
        .put("path", configPath)
      );
    ConfigRetriever retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(store));
    retriever.getConfig(json -> {
      if (json.succeeded()) {
        Utils.setProps(json.result());
        vertx.deployVerticle(KafkaConsumer.class.getName(), defaultDeplOpt);
      }
    });
  }
}
