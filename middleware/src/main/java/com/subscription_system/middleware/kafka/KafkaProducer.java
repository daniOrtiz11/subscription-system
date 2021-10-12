package com.subscription_system.middleware.kafka;

import com.subscription_system.middleware.util.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.UUID;

public class KafkaProducer<T> extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private io.vertx.kafka.client.producer.KafkaProducer<String, T> producer;
	private static final String TOPIC = (String) Utils.getProp("kafka", "topic", "");
	private static final String BOOTSTRAP_SERVERS =
			(String) Utils.getProp("kafka", "bootstrap_server_config", "");
	private static final String CLIENT_ID = (String) Utils.getProp("kafka", "client_id", "");
	private static final boolean ENABLED = (boolean) Utils.getProp("kafka", "enabled", false);
  private EventBus eventBus;
  public static final String KAFKACH = "KafkaProduce";

  @Override
  public void start() {
    eventBus = Vertx.currentContext().owner().eventBus();
    if (ENABLED) {
      Properties config = new Properties();
      config.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
	    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
      config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      this.producer = io.vertx.kafka.client.producer.KafkaProducer.create(vertx, config);
      listen();
    }
	}


	public void produce(Object element) {
    var elementToProduce = (T) element;
		LOGGER.info("Sending to ".concat(TOPIC).concat(" : ".concat(element.toString())));
		producer.send(KafkaProducerRecord.create(TOPIC, UUID.randomUUID().toString(), elementToProduce), done -> {
			if (done.succeeded()) {
				LOGGER.info("Done! Produce: ".concat(element.toString()));
			} else {
				LOGGER.error("Fail! Produce: ".concat(element.toString()));
				LOGGER.error("Summary error: ".concat(done.cause().getMessage()));
				LOGGER.error("Cause error: ".concat(done.cause().getCause().getMessage()));
			}
		});
	}

	public void listen() {
    eventBus.consumer(KAFKACH, objectReceived -> {
      if (objectReceived != null && objectReceived.body() != null) {
        produce(objectReceived.body());
      }
    });
  }
}
