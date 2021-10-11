package com.subscription_system.middleware.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.subscription_system.middleware.Main.start;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json"},
features = "src/acceptance/resources/features/")

public class RunCukeTest {

  @BeforeClass
  public static void main() {
    startAPI();
  }

  public static ConfigStoreOptions store;

  public static void startAPI(){
    String configPath = "acceptance_config.yaml";
    store = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject()
        .put("path", configPath)
      );
    start(store);
  }

}
