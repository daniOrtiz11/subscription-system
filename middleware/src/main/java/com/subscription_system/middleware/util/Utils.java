package com.subscription_system.middleware.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;

import java.lang.reflect.Method;
import java.time.Instant;

public class Utils {
  private static JsonObject jsonProps;

  public static Object getProp(String parent, String key, Object defaultValue) {
    String format = defaultValue.getClass().getSimpleName();
    try {
      Method getValueByFormat = jsonProps.getJsonObject(parent).getClass()
        .getMethod("get".concat(format), key.getClass());
      Object value;
      value = getValueByFormat.invoke(jsonProps.getJsonObject(parent), key);
      if (value == null) {
        value = defaultValue;
      }
      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public static void setProps(JsonObject result) {
    jsonProps = result;
  }

  public static boolean checkMail (String email){
    return EmailValidator.getInstance().isValid(email);
  }

  public static JsonObject readJsonByString(String body) {
    return new JsonObject (body);
  }

  public static JsonArray readJsonArrayByString(String body) {
    return new JsonArray(body);
  }

  public static String generateChannel(String prefix, String suffix) {
    return suffix.concat(prefix) + Instant.now();
  }
}
