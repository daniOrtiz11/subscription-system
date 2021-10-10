package com.subscription_system.public_service.util;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

  private final JsonObject jsonProps = Mockito.mock(JsonObject.class);


  @Test
  void checkToken() {
    assertAll(
      () -> assertTrue(Utils.checkToken("XXXXXXXXXXXXXXX")),
      () -> assertFalse(Utils.checkToken("")),
      () -> assertFalse(Utils.checkToken("XXXXXXXXXXX")),
      () -> assertFalse(Utils.checkToken(null))
    );
  }


  @Test
  void checkMail() {
    assertAll(
      () -> assertFalse(Utils.checkMail("xxxx")),
      () -> assertFalse(Utils.checkMail(null)),
      () -> assertFalse(Utils.checkMail("xxxx.com")),
      () -> assertTrue(Utils.checkMail("xxx@xxx.com"))
    );
  }

  @Test
  @DisplayName("Get config default value")
  void getPropDefaultTest(){
    Utils.setProps(jsonProps);
    Object value = Utils.getProp("parent", "key", "defaultValue");
    assertEquals(value, "defaultValue");
  }

}
