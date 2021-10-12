package com.subscription_system.email_notifier.kafka;

import com.subscription_system.email_notifier.util.Utils;
import io.vertx.core.AbstractVerticle;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class KafkaConsumer extends AbstractVerticle {

	private static final String BOOTSTRAP_SERVERS =
			(String) Utils.getProp("kafka", "bootstrap_server_config", "");
	private static final String GROUP_ID = (String) Utils.getProp("kafka", "group_id", "");
	private static final String CLIENT_ID = (String) Utils.getProp("kafka", "client_id", "");
	private static final Boolean AUTO_COMMIT = (Boolean) Utils.getProp("kafka", "auto_commit", true);
	private static final Integer AUTO_COMMIT_INTERVAL = (Integer) Utils.getProp("kafka", "auto_commit_interval", 1);
	private static final String CONSUMER_MODE =  (String) Utils.getProp("kafka", "consumer_mode", "");
	public static final String LIVE_MODE = "live";
	public static final String BATCH_MODE = "batch";
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private EmailConsumer emailConsumer;

	public void setUp() {
		Properties config = new Properties();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		//new client id value in config:
		config.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, AUTO_COMMIT);
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, AUTO_COMMIT_INTERVAL);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//Use Kafka Avro Deserializer.
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		this.emailConsumer = new EmailConsumer<String>(vertx, config);
	}

	@Override
	public void start() {
		vertx.exceptionHandler(
			error -> LOGGER.info(error.getMessage().concat(" ").concat(error.getLocalizedMessage())));
		setUp();
		modeHandler();
	}

	private void modeHandler() {
		if (CONSUMER_MODE.equalsIgnoreCase(LIVE_MODE)) {
      emailConsumer.live();
		} else if (CONSUMER_MODE.equalsIgnoreCase(BATCH_MODE) || CONSUMER_MODE.isBlank()) {
      emailConsumer.batch();
		} else {
			LOGGER.error("Error getting kafka consumer mode: only live or batch is enabled");
			System.exit(0);
		}
	}
}
