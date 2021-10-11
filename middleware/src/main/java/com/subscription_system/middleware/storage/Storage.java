package com.subscription_system.middleware.storage;

import com.subscription_system.middleware.dto.Subscription;
import com.subscription_system.middleware.util.Utils;
import io.vertx.sqlclient.SqlClient;

public abstract class Storage {

  protected int port;
  protected String user;
  protected String pass;
  protected String host;
  protected String db;
  protected String schema;
  protected String subscription_table;

  public Storage() {
    this.port = (Integer) Utils.getProp("db", "port", 5432);
    this.pass = (String) Utils.getProp("db", "pass", "pass");
    this.user = (String) Utils.getProp("db", "user", "user");
    this.host = (String) Utils.getProp("db", "host", "localhost");
    this.db = (String) Utils.getProp("db", "db", "db");
    this.schema = (String) Utils.getProp("db", "schema", "subscription-sch");
    this.subscription_table = (String) Utils.getProp("db", "subscription_table", "SUBSCRIPTIONS");
  }

  public abstract SqlClient getSQLClient() throws Exception;
  public abstract void insertSubscription(Subscription subscription, String channel);
  public abstract void getSubscription(String email, String newsletterId, String channel);
  public abstract void deleteSubscription(String email, String newsletterId, String channel);
  public abstract void getSubscriptionByEmail(String email, String channel);
}
