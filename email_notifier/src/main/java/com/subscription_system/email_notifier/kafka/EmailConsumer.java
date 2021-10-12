package com.subscription_system.email_notifier.kafka;

import com.subscription_system.email_notifier.service.EmailService;
import com.subscription_system.email_notifier.service.Service;
import com.subscription_system.email_notifier.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.kafka.client.consumer.KafkaConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.*;

public class EmailConsumer<T> {

	private final KafkaConsumer<String, T> consumer;
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String TOPIC = (String) Utils.getProp("kafka", "topic", "");
	private static final Integer POLL_TIME =  (Integer) Utils.getProp("kafka", "poll_time", 2000);
  private Service service;

	public EmailConsumer(Vertx vertx, Properties config) {
		this.consumer = KafkaConsumer.create(vertx, config);
		this.service = new EmailService();
	}

	public void batch() {
		LOGGER.info("Starting kafka consumer in batch mode");
		Set<String> topics = new HashSet<>();
		topics.add(TOPIC);
		consumer.subscribe(topics, ar -> {
			if (ar.succeeded()) {
				LOGGER.info("subscribed to topics: ".concat(topics.toString()));
				this.poll(consumer);
			} else {
				LOGGER.info("Could not subscribe " + ar.cause().getMessage());
			}
		});
	}

	private void poll(KafkaConsumer<String, T> consumer) {
		consumer.poll(Duration.ofMillis(POLL_TIME), ar1 -> {
			if (ar1.succeeded()) {
				KafkaConsumerRecords<String, T> records = ar1.result();
				ArrayList<T> recordsValue = new ArrayList<>();
				for (int i = 0; i < records.size(); i++) {
					KafkaConsumerRecord<String, T> record = records.recordAt(i);
					LOGGER.info("key=" + record.key()
							+ ",value=" + record.value()
							+ ",partition=" + record.partition()
							+ ",offset=" + record.offset()
							+ ",topic=" + record.topic());
					recordsValue.add(record.value());
				}
				if (recordsValue.size() > 0) {
				  this.service.send((ArrayList<Object>) recordsValue);
				}
				poll(consumer);
			}
		});
	}

	public void live() {
		LOGGER.info("Starting kafka consumer in live mode");
		Set<String> topics = new HashSet<>();
		topics.add(TOPIC);
		consumer.subscribe(topics, ar -> {
			if (ar.succeeded()) {
				LOGGER.info("subscribed to topics: ".concat(topics.toString()));
				consumer.handler(record -> {
					LOGGER.info("key=" + record.key()
							+ ",value=" + record.value()
							+ ",partition=" + record.partition()
							+ ",offset=" + record.offset()
							+ ",topic=" + record.topic());
					ArrayList<T> recordsValue = new ArrayList<>();
					recordsValue.add(record.value());
					this.service.send((ArrayList<Object>) recordsValue);
				});
			} else {
				LOGGER.info("Could not subscribe " + ar.cause().getMessage());
			}
		});
	}
}
