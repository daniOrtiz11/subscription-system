package com.subscription_system.middleware.storage;

import com.subscription_system.middleware.adapter.SubscriptionResponseAdapter;
import com.subscription_system.middleware.dto.Subscription;
import com.subscription_system.middleware.dto.SubscriptionResponse;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static com.subscription_system.middleware.adapter.SubscriptionAdapter.sqlRowAdapter;
import static com.subscription_system.middleware.adapter.SubscriptionAdapter.toJsonArrayAdapter;

public class PostgreSQLStorage extends Storage {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final EventBus eventBus = Vertx.currentContext().owner().eventBus();

  public PostgreSQLStorage(){
    super();
  }

  @Override
  public SqlClient getSQLClient() {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(this.port)
      .setHost(this.host)
      .setDatabase(this.db)
      .setUser(this.user)
      .setPassword(this.pass);
    // Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);
    // Create the client pool
    return PgPool.pool(Vertx.currentContext().owner(), connectOptions, poolOptions);
  }

  @Override
  public void insertSubscription(Subscription subscription, String channel) {
    SqlClient client = this.getSQLClient();
    client
      .preparedQuery("INSERT INTO \"" + this.schema + "\".\""+this.subscription_table+"\" " +
        "(email, name, gender, birth_date, consent, newsletter_id) " +
        "VALUES ($1, $2, $3, $4, $5, $6)")
      .execute(Tuple.of(subscription.getEmail(), subscription.getName(), subscription.getGender(),
        subscription.getBirth_date(), subscription.isConsent(), subscription.getNewsletterId()), ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          LOGGER.info(rows.rowCount());
          eventBus.request(channel, subscription.getNewsletterId());
        } else {
          LOGGER.info("Failure: " + ar.cause().getMessage());
          eventBus.request(channel, null);
        }
        client.close();
      });
  }

  @Override
  public void deleteSubscription(String email, String newsletterId, String channel) {
    SqlClient client = this.getSQLClient();
    client
      .preparedQuery("DELETE FROM \"" + this.schema + "\".\""+this.subscription_table+"\" " +
        "WHERE email = $1 and newsletter_id = $2")
      .execute(Tuple.of(email, newsletterId), ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          LOGGER.info(rows.rowCount());
          if (rows.rowCount() > 0) {
            eventBus.request(channel, newsletterId);
          } else {
            eventBus.request(channel, null);
          }
        } else {
          LOGGER.info("Failure: " + ar.cause().getMessage());
          eventBus.request(channel, null);
        }
        client.close();
      });
  }

  @Override
  public void getSubscription(String email, String newsletterId, String channel) {
    SqlClient client = this.getSQLClient();
    String preparedStatement = "SELECT * FROM \"" + this.schema + "\".\"" +this.subscription_table+"\" " +
      "WHERE email = '".concat(email).concat("' and newsletter_id = '".concat(newsletterId.concat("'")));
    client
      .query(preparedStatement)
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          List<Subscription> dtoSubscription = new ArrayList<>();
          for (Row row : rows) {
            dtoSubscription.add(sqlRowAdapter(row));
          }
          if (dtoSubscription.size() > 0) {
            eventBus.request(channel, toJsonArrayAdapter(dtoSubscription).encode());
          } else {
            eventBus.request(channel, null);
          }
        } else {
          LOGGER.info("Failure: " + ar.cause().getMessage());
          eventBus.request(channel, null);
        }
        client.close();
      });
  }

  @Override
  public void getSubscriptionByEmail(String email, String channel) {
    SqlClient client = this.getSQLClient();
    String preparedStatement = "SELECT * FROM \"" + this.schema + "\".\"" +this.subscription_table+"\" " +
      "WHERE email = '".concat(email.concat("'"));
    client
      .query(preparedStatement)
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();
          List<SubscriptionResponse> dtoSubscriptionResponse = new ArrayList<>();
          for (Row row : rows) {
            Subscription subscription = sqlRowAdapter(row);
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription.getNewsletterId());
            dtoSubscriptionResponse.add(subscriptionResponse);
          }
          if (dtoSubscriptionResponse.size() > 0) {
            eventBus.request(channel, SubscriptionResponseAdapter.toJsonArrayAdapter(dtoSubscriptionResponse).encode());
          } else {
            eventBus.request(channel, null);
          }
        } else {
          LOGGER.info("Failure: " + ar.cause().getMessage());
          eventBus.request(channel, null);
        }
        client.close();
      });
  }
}
